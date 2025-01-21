package ceos.phototoground.domain.reservation.service;

import ceos.phototoground.domain.calendar.entity.PhotographerCalendar;
import ceos.phototoground.domain.calendar.service.CalendarService;
import ceos.phototoground.domain.calendar.service.PhotographerCalendarService;
import ceos.phototoground.domain.customer.entity.Customer;
import ceos.phototoground.domain.customer.service.CustomerService;
import ceos.phototoground.domain.email.dto.EmailDTO;
import ceos.phototoground.domain.email.service.EmailService;
import ceos.phototoground.domain.photoProfile.entity.PhotoProfile;
import ceos.phototoground.domain.photoProfile.service.PhotoProfileService;
import ceos.phototoground.domain.photographer.entity.Photographer;
import ceos.phototoground.domain.photographer.service.PhotographerService;
import ceos.phototoground.domain.reservation.dto.DateScheduleDTO;
import ceos.phototoground.domain.reservation.dto.PhotographerReservationInfo;
import ceos.phototoground.domain.reservation.dto.RequestReservationDTO;
import ceos.phototoground.domain.reservation.dto.ReservationInfoDTO;
import ceos.phototoground.domain.reservation.dto.ReservationInfoListDTO;
import ceos.phototoground.domain.reservation.dto.ReservationInfoResponse;
import ceos.phototoground.domain.reservation.dto.ReservationStateDTO;
import ceos.phototoground.domain.reservation.dto.ReservationStatusInfo;
import ceos.phototoground.domain.reservation.entity.Reservation;
import ceos.phototoground.domain.reservation.entity.Status;
import ceos.phototoground.domain.reservation.repository.ReservationRepository;
import ceos.phototoground.domain.schedule.dto.WeekDaySchedule;
import ceos.phototoground.domain.schedule.entity.Schedule;
import ceos.phototoground.domain.schedule.service.ScheduleService;
import ceos.phototoground.domain.univ.entity.PhotographerUniv;
import ceos.phototoground.domain.univ.entity.Univ;
import ceos.phototoground.domain.univ.service.PhotographerUnivService;
import ceos.phototoground.domain.univ.service.UnivService;
import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PhotoProfileService photoProfileService;
    private final ScheduleService scheduleService;
    private final CalendarService calendarService;
    private final PhotographerCalendarService photographerCalendarService;
    private final PhotographerUnivService photographerUnivService;
    private final UnivService univService;
    private final PhotographerService photographerService;
    private final CustomerService customerService;
    private final EmailService emailService;

    // 포그 이메일 계정
    @Value("${spring.mail.username}")
    private String username;

    // 예약신청 페이지 조회
    public PhotographerReservationInfo getPhotographerReservationInfo(Long photographerId) {
        // calendar(photographerCalendar와 연관), schedule(photographer와 연관), univ(photographerUniv와 연관), nickname(photoProfile과 연관), price(photoProfile과 연관), addPrice(photoProfile과 연관)
        PhotoProfile profile = photoProfileService.findByPhotographer_Id(photographerId);

        // 현재 날짜로부터 한달 뒤까지만 조회
        List<PhotographerCalendar> photoCalendar = photographerCalendarService.findByPhotographer_IdAndDateBetween(
                photographerId, LocalDate.now());
        List<String> availDates = calendarService.findByIdIn(photoCalendar);

        // 촬영 가능 대학
        List<PhotographerUniv> photographerUnivList = photographerUnivService.findByPhotographer_Id(photographerId);

        List<String> univName = photographerUnivList.stream()
                .map(list -> list.getUniv().getName())
                .toList();

        // 요일별 일정
        List<Schedule> schedule = scheduleService.findByPhotographer_Id(photographerId);
        List<WeekDaySchedule> weekDaySchedule = scheduleService.getWeekDaySchedules(schedule);

        return PhotographerReservationInfo.of(profile, weekDaySchedule, availDates, univName);
    }


    //예약신청
    @Transactional
    public void createReservation(RequestReservationDTO requestReservationDTO, Long photographerId, Long customerId) {

        Customer customer = customerService.findById(customerId);
        Photographer photographer = photographerService.findById(photographerId);
        Univ univ = univService.findByName(requestReservationDTO.getUnivName());

        Reservation reservation = Reservation.createReservation(customer, photographer, univ, requestReservationDTO);

        EmailDTO emailDTO = new EmailDTO(customer, photographer);
        emailService.sendEmailWithRetry(emailDTO, username);

        reservationRepository.save(reservation);
    }


    //예약취소
    @Transactional
    public void cancelReservation(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
        reservation.changeStatus(Status.CANCELED);

        EmailDTO emailDTO = new EmailDTO(reservation);
        emailService.sendEmailWithRetry(emailDTO, username);

    }


    //예약상세 조회
    public ReservationInfoResponse getOneReservationDetail(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        PhotoProfile profile = photoProfileService.findByPhotographer_Id(reservation.getPhotographer().getId());

        return ReservationInfoResponse.of(reservation, profile);
    }


    //예약 현황 조회
    public ReservationStatusInfo getReservationStatus(Long customerId) {

        List<Reservation> reservations = reservationRepository.findByCustomer_IdAfterToday(customerId, LocalDate.now());

        List<DateScheduleDTO> dateSchedules = reservations.stream().map(DateScheduleDTO::from).toList();

        return ReservationStatusInfo.from(dateSchedules);
    }


    // 진행중인 스냅 전체 조회 (촬영완료 제외 단계)
    public ReservationInfoListDTO getReservationList(Long customerId) {

        List<Reservation> reservations = reservationRepository.findByCustomer_IdAndStatusNot(customerId,
                Status.COMPLETED);

        List<Reservation> filteredReservations = reservations.stream().filter(reservation -> {
                    if (reservation.getStatus() == Status.CANCELED) { //enum은 ==로 비교
                        LocalDate updatedAt = reservation.getUpdatedAt().toLocalDate();
                        return ChronoUnit.DAYS.between(updatedAt, LocalDate.now()) <= 7;
                    } else {
                        return true; //다른 status는 모두 통과
                    }
                })
                .toList();

        List<ReservationInfoDTO> dtos = filteredReservations.stream().map(ReservationInfoDTO::from).toList();
        return ReservationInfoListDTO.from(dtos);
    }


    // 지난 스냅 전체 조회 (촬영완료만)
    public ReservationInfoListDTO getCompleteReservationList(Long customerId) {

        List<Reservation> reservations = reservationRepository.findByCustomer_IdAndStatus(customerId, Status.COMPLETED);

        List<ReservationInfoDTO> dtos = reservations.stream().map(ReservationInfoDTO::from).toList();
        return ReservationInfoListDTO.from(dtos);
    }


    @Transactional
    // 입금 확인 요청
    public ReservationStateDTO requestCheckPayment(Long reservationId, Long customerId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        reservation.changeStatus(Status.PAYMENT_PENDING);

        Customer customer = customerService.findById(customerId);
        emailService.sendEmailWithRetry(new EmailDTO(customer, reservationId), username);

        return ReservationStateDTO.of(reservation.getId(), "결제확인중");

    }


}

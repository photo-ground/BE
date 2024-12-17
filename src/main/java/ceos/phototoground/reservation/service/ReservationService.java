package ceos.phototoground.reservation.service;

import ceos.phototoground.calendar.domain.PhotographerCalendar;
import ceos.phototoground.calendar.service.CalendarService;
import ceos.phototoground.calendar.service.PhotographerCalendarService;
import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.photoProfile.service.PhotoProfileService;
import ceos.phototoground.reservation.dto.PhotographerReservationInfo;
import ceos.phototoground.reservation.repository.ReservationRepository;
import ceos.phototoground.schedule.domain.Schedule;
import ceos.phototoground.schedule.dto.WeekDaySchedule;
import ceos.phototoground.schedule.service.ScheduleService;
import ceos.phototoground.univ.domain.PhotographerUniv;
import ceos.phototoground.univ.service.PhotographerUnivService;
import ceos.phototoground.univ.service.UnivService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

    // 예약신청 페이지 조회
    public PhotographerReservationInfo getPhotographerReservationInfo(Long photographerId) {
        // calendar(photographerCalendar와 연관), schedule(photographer와 연관), univ(photographerUniv와 연관), nickname(photoProfile과 연관), price(photoProfile과 연관), addPrice(photoProfile과 연관)
        PhotoProfile profile = photoProfileService.findByPhotographer_Id(photographerId);

        List<PhotographerCalendar> photoCalendar = photographerCalendarService.findByPhotographer_Id(photographerId);
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
}

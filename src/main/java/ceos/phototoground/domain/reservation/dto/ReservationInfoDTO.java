package ceos.phototoground.domain.reservation.dto;

import ceos.phototoground.domain.reservation.entity.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationInfoDTO {

    private Long reservationId;
    private String photographerName;
    private String profileImage;
    private String univName;
    private int bookingNum;
    private String status;
    private int price;
    private LocalDate date;
    private LocalTime startTime;
    private String chatUrl;

    public static ReservationInfoDTO from(Reservation reservation) {
        return ReservationInfoDTO.builder()
                .reservationId(reservation.getId())
                .photographerName(reservation.getPhotographer().getPhotoProfile().getNickname())
                .profileImage(reservation.getPhotographer().getPhotoProfile().getProfileUrl())
                .univName(reservation.getUniv().getName())
                .bookingNum(reservation.getReserveNum())
                .status(reservation.getStatus().getName())
                .price(reservation.getPrice())
                .date(reservation.getDate())
                .startTime(reservation.getStartTime())
                .chatUrl(reservation.getChatUrl())
                .build();
    }
}

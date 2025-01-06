package ceos.phototoground.domain.reservation.dto;

import ceos.phototoground.domain.reservation.entity.Reservation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DateScheduleDTO {

    private Long reservationId;
    private String date;
    private String startTime;
    private String photographerName;
    private String university;
    private String chatUrl;
    private int reserveNum;

    
    public static DateScheduleDTO from(Reservation reservation) {
        return DateScheduleDTO.builder()
                .reservationId(reservation.getId())
                .date(reservation.getDate().toString())
                .startTime(reservation.getStartTime().toString())
                .photographerName(reservation.getPhotographer().getPhotoProfile().getNickname())
                .university(reservation.getUniv().getName())
                .chatUrl(reservation.getChatUrl())
                .reserveNum(reservation.getReserveNum())
                .build();
    }
}

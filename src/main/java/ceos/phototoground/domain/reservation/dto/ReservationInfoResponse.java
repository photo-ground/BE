package ceos.phototoground.domain.reservation.dto;

import ceos.phototoground.domain.photoProfile.entity.PhotoProfile;
import ceos.phototoground.domain.reservation.entity.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationInfoResponse {

    private String photographerName;
    private String photographerProfileUrl;
    private String univName;
    private int reserveNum;
    private LocalDate date;
    private LocalTime startTime;
    private String requirement;
    private String status;


    public static ReservationInfoResponse of(Reservation reservation, PhotoProfile profile) {
        return ReservationInfoResponse.builder()
                .photographerName(profile.getNickname())
                .photographerProfileUrl(profile.getProfileUrl())
                .univName(reservation.getUniv().getName())
                .reserveNum(reservation.getReserveNum())
                .date(reservation.getDate())
                .startTime(reservation.getStartTime())
                .requirement(reservation.getRequirement())
                .status(reservation.getStatus().getName())
                .build();
    }
}

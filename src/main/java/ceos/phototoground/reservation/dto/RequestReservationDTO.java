package ceos.phototoground.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestReservationDTO {

    private String univName;
    private int reserveNum;
    private int price;
    private LocalDate date;
    private LocalTime startTime;
    private String requirement;

}

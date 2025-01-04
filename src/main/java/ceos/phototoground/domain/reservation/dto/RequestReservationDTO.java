package ceos.phototoground.domain.reservation.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestReservationDTO {

    @NotNull(message = "대학 이름은 null값이면 안됩니다.")
    private String univName;

    @NotNull(message = "예약 인원 수는 null값이면 안됩니다.")
    private int reserveNum;

    @NotNull(message = "가격은 null값이면 안됩니다.")
    private int price;

    @NotNull(message = "날짜는 null값이면 안됩니다.")
    @Future(message = "예약날짜는 오늘 이후의 날짜여야 합니다.")
    private LocalDate date;

    @NotNull(message = "시작시간은 null값이면 안됩니다.")
    private LocalTime startTime;
    
    private String requirement;

}

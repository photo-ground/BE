package ceos.phototoground.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationStateDTO {

    private Long reservationId;
    private String state;

    public static ReservationStateDTO of(Long reservationId, String state) {
        return ReservationStateDTO.builder()
                .reservationId(reservationId)
                .state(state).build();
    }
}

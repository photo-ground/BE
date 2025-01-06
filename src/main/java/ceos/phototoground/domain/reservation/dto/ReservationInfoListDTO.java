package ceos.phototoground.domain.reservation.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationInfoListDTO {

    private List<ReservationInfoDTO> reservationInfoDTOList;

    public static ReservationInfoListDTO from(List<ReservationInfoDTO> dtos) {
        return new ReservationInfoListDTO(dtos);
    }
}

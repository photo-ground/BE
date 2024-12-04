package ceos.phototoground.photographer.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhotographerSearchListDTO {

    private List<PhotographerResponseDTO> photographerList;
    private boolean hasNext;
    private String nextCursor; //여기 담긴 값을 실어 다음 요청 보내면 됨

    public static PhotographerSearchListDTO of(List<PhotographerResponseDTO> dtos, boolean hasNext, String nextCursor) {
        return PhotographerSearchListDTO.builder()
                .photographerList(dtos)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .build();
    }
}

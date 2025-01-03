package ceos.phototoground.domain.photographer.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhotographerListDTO {
    
    private List<PhotographerResponseDTO> photographerList;
    private boolean hasNext;

    public static PhotographerListDTO of(List<PhotographerResponseDTO> dtos, boolean hasNext) {
        return PhotographerListDTO.builder()
                .photographerList(dtos)
                .hasNext(hasNext)
                .build();
    }
}

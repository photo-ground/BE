package ceos.phototoground.domain.photographer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PhotographerIdDTO {

    private Long photographerId;

    public static PhotographerIdDTO from(Long photographerId) {
        return new PhotographerIdDTO(photographerId);
    }
}

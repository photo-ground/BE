package ceos.phototoground.domain.photographer.dto;

import ceos.phototoground.domain.post.dto.ProfilePostResponseListDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhotographerBottomDTO {

    private ProfilePostResponseListDTO posts;

    public static PhotographerBottomDTO from(ProfilePostResponseListDTO postList) {
        return PhotographerBottomDTO.builder()
                .posts(postList)
                .build();
    }
}

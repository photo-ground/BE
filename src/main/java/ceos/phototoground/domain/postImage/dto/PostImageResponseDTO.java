package ceos.phototoground.domain.postImage.dto;

import ceos.phototoground.domain.postImage.entity.PostImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostImageResponseDTO {
    private String imageUrl;
    private String spotName;
    private Long imageId;

    public static PostImageResponseDTO from(PostImage postImage) {
        return PostImageResponseDTO.builder()
                .imageUrl(postImage.getImageUrl())
                .spotName(postImage.getSpot().getName())
                .imageId(postImage.getId())
                .build();
    }
}

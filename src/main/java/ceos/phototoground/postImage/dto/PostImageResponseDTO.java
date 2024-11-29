package ceos.phototoground.postImage.dto;

import ceos.phototoground.postImage.domain.PostImage;
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

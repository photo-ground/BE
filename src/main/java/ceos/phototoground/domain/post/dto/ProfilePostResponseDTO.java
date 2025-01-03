package ceos.phototoground.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProfilePostResponseDTO {

    private Long postId;
    private String firstImageUrl;
}

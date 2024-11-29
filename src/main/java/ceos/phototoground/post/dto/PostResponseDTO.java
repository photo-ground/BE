package ceos.phototoground.post.dto;

import ceos.phototoground.post.domain.Post;
import ceos.phototoground.postImage.dto.PostImageResponseDTO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponseDTO {
    private Long id;
    private Long photographerId;
    private String photographerName;
    private String content;
    private String univName;
    private List<PostImageResponseDTO> imageList;
    private LocalDateTime createdAt;

    public static PostResponseDTO of(Post post, List<PostImageResponseDTO> imageListDto) {
        return PostResponseDTO.builder()
                .id(post.getId())
                .photographerId(post.getPhotographer().getId())
                .photographerName(post.getPhotographer().get)
                .content(post.getContent())
                .univName(post.getUniv().getName())
                .imageList(imageListDto)
                .createdAt(post.getCreatedAt())
                .build();
    }
}

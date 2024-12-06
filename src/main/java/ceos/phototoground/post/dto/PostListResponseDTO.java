package ceos.phototoground.post.dto;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.post.domain.Post;
import ceos.phototoground.spot.domain.Spot;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListResponseDTO {
    private Long id;
    private Long photographerId;
    private String photographerName;
    private String firstImageUrl;
    private String firstImageSpot;
    private LocalDateTime createdAt;
    

    public static PostListResponseDTO of(Post post, PhotoProfile profile, Spot spot) {
        return PostListResponseDTO.builder()
                .id(post.getId())
                .photographerId(post.getPhotographer().getId())
                .photographerName(
                        profile != null
                                ? profile.getNickname()
                                : "게시글의 작가를 찾을 수 없습니다." // 기본값
                )
                .firstImageUrl(post.getFirstImageUrl())
                .firstImageSpot(
                        spot != null
                                ? spot.getName()
                                : "이미지 스팟을 찾을 수 없습니다." // 기본값
                )
                .createdAt(post.getCreatedAt())
                .build();
    }
}

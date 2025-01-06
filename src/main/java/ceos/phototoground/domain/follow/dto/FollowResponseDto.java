package ceos.phototoground.domain.follow.dto;

import ceos.phototoground.domain.photographer.entity.Photographer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowResponseDto {
    private String photographerName;
    private String profileUrl;
    private Long photographerId;

    // 정적 메서드: Photographer와 관련 엔티티에서 DTO로 변환
    public static FollowResponseDto from(Photographer photographer) {
        return FollowResponseDto.builder()
                                      .photographerName(photographer.getPhotoProfile().getNickname()) // 프로필 이름
                                      .profileUrl(photographer.getPhotoProfile().getProfileUrl()) // 프로필 이미지 URL
                                      .photographerId(photographer.getId())
                                      .build();
    }
}

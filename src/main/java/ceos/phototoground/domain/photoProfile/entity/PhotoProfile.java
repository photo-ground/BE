package ceos.phototoground.domain.photoProfile.entity;

import ceos.phototoground.global.entity.BaseTimeEntity;
import ceos.phototoground.domain.photographer.entity.Photographer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class PhotoProfile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @Column(nullable = false)
    private String nickname; // 프로필명

    @Column(nullable = false)
    private Long price; // 가격 (1인 기준)

    @Column(nullable = false)
    private Long addPrice; // 인원 추가 시 가격

    @Column(nullable = false)
    private String profileUrl; // 프로필 이미지 URL

    @Column(columnDefinition = "TEXT")
    private String introduction; // 작가 소개글

    private Long followerNum; // 팔로워 수

    private String camera; // 보유한 카메라 정보

    private String bank; // 은행명

    private String account; // 계좌 번호

    private Long score; // 별점

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographer_id")
    private Photographer photographer;

    @OneToMany(mappedBy = "photoProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoStyle> photoStyles; // 작가의 촬영 스타일
}

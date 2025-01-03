package ceos.phototoground.domain.post.entity;

import ceos.phototoground.global.entity.BaseTimeEntity;
import ceos.phototoground.domain.photographer.entity.Photographer;
import ceos.phototoground.domain.univ.entity.Univ;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(length = 1024) // 최대 1024자까지 허용
    private String content;

    @Column(length = 1024)
    private String firstImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographer_id")
    private Photographer photographer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "univ_id")
    private Univ univ;


    public void mapFirstImageUrl(String firstImageUrl) {
        this.firstImageUrl = firstImageUrl;
    }
}

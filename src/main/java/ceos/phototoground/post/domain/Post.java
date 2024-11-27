package ceos.phototoground.post.domain;

import ceos.phototoground.global.BaseTimeEntity;
import ceos.phototoground.photographer.domain.Photographer;
import ceos.phototoground.univ.domain.Univ;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    @Column(length = 1024) // 최대 1024자까지 허용
    private String content;

    private String firstImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="photographer_id")
    private Photographer photographer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="univ_id")
    private Univ univ;


    public void mapFirstImageUrl(String firstImageUrl) {
        this.firstImageUrl = firstImageUrl;
    }
}

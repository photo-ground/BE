package ceos.phototoground.postImage.domain;

import ceos.phototoground.global.BaseTimeEntity;
import ceos.phototoground.post.domain.Post;
import ceos.phototoground.spot.domain.Spot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="postImage_id")
    private Long id;

    @NotNull
    private String imageUrl;

    @NotNull
    private String originalFileName;

    private int imageOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="spot_id")
    private Spot spot;
}

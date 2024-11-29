package ceos.phototoground.spot.domain;

import ceos.phototoground.global.BaseTimeEntity;
import ceos.phototoground.univ.domain.Univ;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Spot extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="spot_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Float latitude;

    @NotNull
    private Float longitude;

    @Column(length = 1024) // 최대 1024자까지 허용
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="univ_id")
    private Univ univ;
}

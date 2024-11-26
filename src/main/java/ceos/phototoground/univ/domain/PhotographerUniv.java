package ceos.phototoground.univ.domain;

import ceos.phototoground.global.BaseTimeEntity;
import ceos.phototoground.photographer.domain.Photographer;
import jakarta.persistence.*;
import lombok.*;

//작가, 촬영가능 대학교 연관테이블
@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class PhotographerUniv extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="photographer_univ_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="photographer_id")
    private Photographer photographer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="univ_id")
    private Univ univ;

}

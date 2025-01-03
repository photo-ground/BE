package ceos.phototoground.domain.univ.entity;

import ceos.phototoground.global.entity.BaseTimeEntity;
import ceos.phototoground.domain.photographer.entity.Photographer;
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

package ceos.phototoground.univ.domain;

import ceos.phototoground.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Univ extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="univ_id")
    private Long id;

    @NotNull
    private String name;
}
package ceos.phototoground.photographer.domain;

import ceos.phototoground.global.BaseTimeEntity;
import ceos.phototoground.photoProfile.domain.PhotoProfile;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
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
public class Photographer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photographer_id")
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String phone;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    private int bornYear;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MyUniv myUniv; //작가의 대학교

    @OneToOne(mappedBy = "photographer", cascade = CascadeType.ALL, orphanRemoval = true)
    private PhotoProfile photoProfile; //양방향 연관관계 설정

}

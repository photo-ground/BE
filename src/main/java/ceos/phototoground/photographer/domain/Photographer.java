package ceos.phototoground.photographer.domain;

import ceos.phototoground.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Photographer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="photographer_id")
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

    @Column(nullable = false)
    private int followerCount = 0; // 팔로워 수

    @NotNull
    @Enumerated(EnumType.STRING)
    private MyUniv myUniv; //작가의 대학교
}

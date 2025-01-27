package ceos.phototoground.domain.photographer.entity;

import ceos.phototoground.domain.customer.entity.UserRole;
import ceos.phototoground.domain.photoProfile.entity.PhotoProfile;
import ceos.phototoground.global.entity.BaseTimeEntity;
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

    // 첫 로그인 여부 확인
    @Builder.Default
    private boolean isFirst = true;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MyUniv myUniv; //작가의 대학교

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role; // 역할 추가

    @OneToOne(mappedBy = "photographer", cascade = CascadeType.ALL, orphanRemoval = true)
    private PhotoProfile photoProfile; //양방향 연관관계 설정

    // 첫 로그인 상태를 false로 변경하는 메서드
    public void markAsLoggedIn() {
        if (this.isFirst) {
            this.isFirst = false;
        }
    }

    // 비밀번호 수정 메서드
    public void updatePassword(String password) {
        this.password = password;
        System.out.println("업데이트 완료");
    }
}

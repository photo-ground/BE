package ceos.phototoground.domain.customer.entity;

import ceos.phototoground.global.entity.BaseTimeEntity;
import ceos.phototoground.domain.photographer.entity.Gender;
import ceos.phototoground.domain.photographer.entity.MyUniv;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Customer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String phone;

    private boolean isDeleted = false; // 삭제 여부 (default: false)

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role; // 역할 추가

    @Enumerated(EnumType.STRING)
    private MyUniv myUniv;

    // 회원 탈퇴 처리 메서드
    public void delete() {
        this.isDeleted = true;
    }

    // 회원정보 수정 메서드
    public void updateProfile(String name, String phone, Gender gender, MyUniv myUniv) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.myUniv = myUniv;
    }
}

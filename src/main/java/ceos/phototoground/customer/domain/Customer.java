package ceos.phototoground.customer.domain;

import ceos.phototoground.global.entity.BaseTimeEntity;
import ceos.phototoground.photographer.domain.Gender;
import ceos.phototoground.photographer.domain.MyUniv;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
    @Column(name="customer_id")
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role; // 역할 추가

    @Enumerated(EnumType.STRING)
    private MyUniv myUniv;

}

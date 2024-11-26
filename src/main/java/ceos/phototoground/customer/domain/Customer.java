package ceos.phototoground.customer.domain;

import ceos.phototoground.global.BaseTimeEntity;
import ceos.phototoground.photographer.domain.Gender;
import ceos.phototoground.photographer.domain.MyUniv;
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
public class Customer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id")
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
    private MyUniv myUniv;
}

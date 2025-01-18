package ceos.phototoground.domain.customer.dto;

import ceos.phototoground.domain.customer.entity.Customer;
import ceos.phototoground.domain.photographer.entity.Gender;
import ceos.phototoground.domain.photographer.entity.MyUniv;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerUpdateDto {
    @NotNull
    private String name; // 이름

    private String phone; // 전화번호

    @Enumerated(EnumType.STRING)
    private MyUniv myUniv; // 대학

    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별
}

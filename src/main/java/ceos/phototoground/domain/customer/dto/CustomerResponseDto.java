package ceos.phototoground.domain.customer.dto;

import ceos.phototoground.domain.customer.entity.Customer;
import ceos.phototoground.domain.photographer.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Gender gender;
    private String univ;

    // 정적 메서드: Entity → DTO 변환
    public static CustomerResponseDto fromEntity(Customer customer) {
        return CustomerResponseDto.builder()
                                  .id(customer.getId())
                                  .name(customer.getName())
                                  .email(customer.getEmail())
                                  .phone(customer.getPhone())
                                  .gender(customer.getGender())
                                  .univ(customer.getMyUniv().getName())
                                  .build();
    }
}

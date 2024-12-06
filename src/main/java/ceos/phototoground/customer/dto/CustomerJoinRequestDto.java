package ceos.phototoground.customer.dto;

import ceos.phototoground.customer.domain.Customer;
import ceos.phototoground.photographer.domain.Gender;
import ceos.phototoground.photographer.domain.MyUniv;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@AllArgsConstructor // 모든 필드에 대한 생성자 자동 생성
@NoArgsConstructor  // 기본 생성자 자동 생성 (필요 시)
public class CustomerJoinRequestDto {
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;
    private String phone;
    private Gender gender;
    private MyUniv myUniv;

    // DTO -> Entity 변환
    public Customer toEntity(String encryptedPassword, String role) {
        return Customer.builder()
                       .email(this.email)
                       .password(encryptedPassword) // 암호화된 비밀번호 전달
                       .phone(this.phone)
                       .gender(this.gender)
                       .role(role)
                       .myUniv(this.myUniv)
                       .build();
    }
}
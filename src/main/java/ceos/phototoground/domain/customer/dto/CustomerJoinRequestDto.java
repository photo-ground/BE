package ceos.phototoground.domain.customer.dto;

import ceos.phototoground.domain.customer.entity.Customer;
import ceos.phototoground.domain.customer.entity.UserRole;
import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import ceos.phototoground.domain.photographer.entity.Gender;
import ceos.phototoground.domain.photographer.entity.MyUniv;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor // 모든 필드에 대한 생성자 자동 생성
@NoArgsConstructor  // 기본 생성자 자동 생성 (필요 시)
public class CustomerJoinRequestDto {
    @NotNull(message = "이메일은 필수 입력값입니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String email;

    @NotNull(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    private String phone;

    @NotNull(message = "성별은 필수 입력값입니다.")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "학교는 필수 입력값입니다.")
    @Enumerated(EnumType.STRING)
    private MyUniv myUniv;

    @NotNull(message = "사용자 이름은 필수 입력값입니다.")
    private String name;


    // DTO -> Entity 변환
    public Customer toEntity(String encryptedPassword, UserRole role) {
        return Customer.builder()
                .email(this.email)
                .password(encryptedPassword) // 암호화된 비밀번호 전달
                .phone(this.phone)
                .gender(this.gender)
                .role(role)
                .myUniv(this.myUniv)
                .name(this.name)
                .build();
    }

    public void validatePassword() {
        if (password == null || password.length() < 8) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD, "비밀번호는 최소 8자 이상이어야 합니다.");
        }
        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!\"#$%&'()*+,\\-./:;<=>?@[\\\\]^_`{|}~]).{8,}$")) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD, "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다.");
        }

    }
}
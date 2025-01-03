package ceos.phototoground.domain.customer.dto;

import lombok.Getter;

@Getter
public class VerificationDTO {
    private String email;
    private String verificationCode;
}

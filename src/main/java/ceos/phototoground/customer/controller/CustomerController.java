package ceos.phototoground.customer.controller;

import ceos.phototoground.customer.dto.CustomerEmailDTO;
import ceos.phototoground.customer.dto.CustomerJoinRequestDto;
import ceos.phototoground.customer.dto.VerificationDTO;
import ceos.phototoground.customer.service.CustomerService;
import ceos.phototoground.email.service.EmailService;
import ceos.phototoground.global.dto.SuccessResponseDto;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String username;

    @PostMapping("/join")
    public ResponseEntity joinProcess(@Valid @RequestBody CustomerJoinRequestDto customerJoinRequestDto) {
        customerService.joinCustomer(customerJoinRequestDto);

        return ResponseEntity.ok(
                SuccessResponseDto.successMessage("회원가입이 완료되었습니다.")
        );
    }

    // 이메일 인증번호 요청
    @PostMapping("/emails/request")
    public ResponseEntity<Map<String, String>> sendVerificationCode(@RequestBody CustomerEmailDTO emailDTO) {
        emailService.sendVerificationCode(emailDTO);

        Map<String, String> response = new HashMap<>();
        response.put("message", "인증번호 전송이 완료되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 이메일 인증
    @PostMapping("/emails/verify")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestBody VerificationDTO verification) {
        boolean isVerified = emailService.verifyCode(verification);

        Map<String, String> response = new HashMap<>();
        
        if (isVerified) {
            response.put("message", "인증이 성공했습니다.");
            return ResponseEntity.ok(response); // 200 OK
        } else {
            response.put("message", "인증이 실패했습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401 Unauthorized
        }
    }
}

package ceos.phototoground.domain.customer.controller;

import ceos.phototoground.domain.customer.dto.CustomUserDetails;
import ceos.phototoground.domain.customer.dto.CustomerEmailDTO;
import ceos.phototoground.domain.customer.dto.CustomerJoinRequestDto;
import ceos.phototoground.domain.customer.dto.CustomerResponseDto;
import ceos.phototoground.domain.customer.dto.CustomerUpdateDto;
import ceos.phototoground.domain.customer.dto.VerificationDTO;
import ceos.phototoground.domain.customer.service.CustomerService;
import ceos.phototoground.domain.email.service.EmailService;
import ceos.phototoground.global.dto.SuccessResponseDto;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;
    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String username;

    // 고객 회원가입
    @PostMapping("/join")
    public ResponseEntity<SuccessResponseDto<String>> joinProcess(
            @Valid @RequestBody CustomerJoinRequestDto customerJoinRequestDto) {
        customerService.joinCustomer(customerJoinRequestDto);

        return ResponseEntity.ok(
                SuccessResponseDto.successMessage("회원가입이 완료되었습니다.")
        );
    }


    // 고객 프로필 조회
    @GetMapping
    public ResponseEntity<CustomerResponseDto> getCustomerProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long customerId = userDetails.getCustomer()
                                     .getId();
        CustomerResponseDto response = customerService.getCustomerProfile(customerId);
        return ResponseEntity.ok(response);
    }

    // 고객 프로필 수정 (이름, 전화번호, 대학, 성별)
    @PatchMapping
    public ResponseEntity<SuccessResponseDto<String>> updateCustomerProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails, // 인증된 사용자 정보
            @Valid @RequestBody CustomerUpdateDto updateDto) {

        // 인증된 사용자 ID 가져오기
        Long customerId = userDetails.getCustomer().getId();

        // 서비스 계층에 요청 전달
        customerService.updateCustomerProfile(customerId, updateDto);

        return ResponseEntity.ok(
                SuccessResponseDto.successMessage("프로필이 성공적으로 수정되었습니다.")
        );
    }


    // 고객 비밀번호 수정

    // 고객 탈퇴 (소프트 탈퇴)
    @PatchMapping("/delete")
    public ResponseEntity<SuccessResponseDto<String>> deleteCustomer(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long customerId = userDetails.getCustomer()
                                     .getId();
        customerService.deleteCustomer(customerId);

        return ResponseEntity.ok(
                SuccessResponseDto.successMessage("회원 탈퇴가 완료되었습니다.")
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(response); // 401 Unauthorized
        }
    }
}

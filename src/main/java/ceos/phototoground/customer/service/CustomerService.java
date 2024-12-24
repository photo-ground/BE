package ceos.phototoground.customer.service;

import ceos.phototoground.customer.domain.Customer;
import ceos.phototoground.customer.domain.UserRole;
import ceos.phototoground.customer.dto.CustomerJoinRequestDto;
import ceos.phototoground.customer.repository.CustomerRepository;
import ceos.phototoground.email.service.EmailService;
import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    public void joinCustomer(CustomerJoinRequestDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();

        // 회원 검증
        boolean isExits = customerRepository.existsByEmail(email);
        if (isExits) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        // 비밀번호 유효성 검증 & 암호화
        dto.validatePassword();

        // 이메일 인증 여부 확인
        if (emailService.isCertified(dto.getEmail())) {
            // 기본 유저 권한은 USER로 설정
            Customer customer = dto.toEntity(bCryptPasswordEncoder.encode(password), UserRole.USER);
            customerRepository.save(customer);
        } else {
            throw new CustomException(ErrorCode.EMAIL_NOT_CERTIFIED);
        }
    }

    public Customer findById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));
    }
}

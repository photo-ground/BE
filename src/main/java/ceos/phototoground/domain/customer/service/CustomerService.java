package ceos.phototoground.domain.customer.service;

import ceos.phototoground.domain.customer.dto.CustomerJoinRequestDto;
import ceos.phototoground.domain.customer.dto.CustomerResponseDto;
import ceos.phototoground.domain.customer.dto.CustomerUpdateDto;
import ceos.phototoground.domain.customer.entity.Customer;
import ceos.phototoground.domain.customer.entity.UserRole;
import ceos.phototoground.domain.customer.repository.CustomerRepository;
import ceos.phototoground.domain.email.service.EmailService;
import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    // 고객 회원가입
    public void joinCustomer(CustomerJoinRequestDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();

        // 회원 검증 -> 이메일 인증에서

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

    // 고객 회원 탈퇴
    public void deleteCustomer(Long customerId) {
        // 회원 정보 조회
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));

        // 소프트 삭제 처리
        customer.delete();
    }

    // 고객 프로필 조회
    public CustomerResponseDto getCustomerProfile(Long customerId) {
        // 회원 조회
        Customer customer = customerRepository.findById(customerId)
                                              .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));

        return CustomerResponseDto.fromEntity(customer);
    }

    @Transactional
    public void updateCustomerProfile(Long customerId, CustomerUpdateDto updateDto) {
        // 고객 엔티티 조회
        Customer customer = customerRepository.findById(customerId)
                                              .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));

        // 제공된 데이터 업데이트
        // 변경 사항은 JPA의 영속성 컨텍스트에 의해 자동 저장
        customer.updateProfile(updateDto.getName(), updateDto.getPhone(), updateDto.getGender(), updateDto.getMyUniv());
    }

    public Customer findById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));
    }

}

package ceos.phototoground.customer.service;

import ceos.phototoground.customer.domain.Customer;
import ceos.phototoground.customer.dto.CustomerJoinRequestDto;
import ceos.phototoground.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinCustomer(CustomerJoinRequestDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();

        // 회원 검증
        boolean isExits = customerRepository.existsByEmail(email);
        if (isExits) {
            return;
        }

        // 비밀번호 암호화
        Customer customer = dto.toEntity(bCryptPasswordEncoder.encode(password), "ROLE_ADMIN");
        customerRepository.save(customer);
    }
}

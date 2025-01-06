package ceos.phototoground.domain.customer.service;

import ceos.phototoground.domain.customer.dto.CustomUserDetails;
import ceos.phototoground.domain.customer.entity.Customer;
import ceos.phototoground.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Optional에서 사용자 정보를 가져오거나 예외를 던짐
        Customer customer = customerRepository.findByEmail(email)
                                              .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // UserDetails로 래핑하여 반환
        return new CustomUserDetails(customer);
    }
}


package ceos.phototoground.customer.service;

import ceos.phototoground.customer.dto.CustomUserDetails;
import ceos.phototoground.customer.domain.Customer;
import ceos.phototoground.customer.repository.CustomerRepository;
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
        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {
            return new CustomUserDetails(customer);
        }

        return null;
    }
}

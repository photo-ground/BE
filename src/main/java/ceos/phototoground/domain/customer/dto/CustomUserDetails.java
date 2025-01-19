package ceos.phototoground.domain.customer.dto;

import ceos.phototoground.domain.customer.entity.Customer;
import ceos.phototoground.domain.photographer.entity.Photographer;
import java.util.ArrayList;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Customer customer;
    private final Photographer photographer;

    public CustomUserDetails(Customer customer) {
        this.customer = customer;
        this.photographer = null;
    }

    public CustomUserDetails(Photographer photographer) {
        this.customer = null;
        this.photographer = photographer;
    }

    public Customer getCustomer() {
        if (customer == null) {
            throw new IllegalStateException("현재 인증된 유저가 고객이 아닙니다.");
        }
        return customer;
    }

    public Photographer getPhotographer() {
        if (photographer == null) {
            throw new IllegalStateException("현재 인증된 유저가 작가가 아닙니다.");
        }
        return photographer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        String authority = customer != null ? customer.getRole().getAuthority()
                : photographer.getRole().getAuthority();

        authorities.add(() -> authority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return customer != null ? customer.getPassword() : photographer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer != null ? customer.getEmail() : photographer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
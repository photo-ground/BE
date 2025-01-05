package ceos.phototoground.domain.customer.repository;

import ceos.phototoground.domain.customer.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    // Repository 메서드 정의 수정
    Optional<Customer> findByEmail(String email);

}

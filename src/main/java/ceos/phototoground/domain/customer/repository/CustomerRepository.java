package ceos.phototoground.domain.customer.repository;

import ceos.phototoground.domain.customer.entity.Customer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    // Repository 메서드 정의 수정
    Optional<Customer> findByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.isDeleted = false")
    List<Customer> findAllActiveCustomers();

}

package ceos.phototoground.customer.repository;

import ceos.phototoground.customer.domain.Customer;
import ceos.phototoground.photoProfile.domain.PhotoProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

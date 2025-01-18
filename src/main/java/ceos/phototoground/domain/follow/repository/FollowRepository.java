package ceos.phototoground.domain.follow.repository;

import ceos.phototoground.domain.customer.entity.Customer;
import ceos.phototoground.domain.follow.entity.Follow;
import ceos.phototoground.domain.photographer.entity.Photographer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByCustomerAndPhotographer(Customer customer, Photographer photographer);

    Optional<Follow> findByCustomerAndPhotographer(Customer customer, Photographer photographer);

    List<Follow> findByCustomer(Customer customer);

    Follow findByCustomer_IdAndPhotographer_Id(Long customerId, Long photographerId);
}

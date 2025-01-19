package ceos.phototoground.domain.photographer.repository;

import ceos.phototoground.domain.customer.entity.Customer;
import ceos.phototoground.domain.photographer.entity.Photographer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PhotographerRepository extends JpaRepository<Photographer, Long>, PhotographerRepositoryCustom {

    Optional<Photographer> findByEmail(String email);
}
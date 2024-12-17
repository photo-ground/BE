package ceos.phototoground.univ.repository;

import ceos.phototoground.univ.domain.Univ;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnivRepository extends JpaRepository<Univ, Long> {
    
    Optional<Univ> findByName(String univ);
}

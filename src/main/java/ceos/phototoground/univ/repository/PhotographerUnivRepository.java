package ceos.phototoground.univ.repository;

import ceos.phototoground.univ.domain.PhotographerUniv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotographerUnivRepository extends JpaRepository<PhotographerUniv,Long> {
}

package ceos.phototoground.domain.univ.repository;

import ceos.phototoground.domain.univ.entity.PhotographerUniv;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotographerUnivRepository extends JpaRepository<PhotographerUniv, Long> {

    @Query("SELECT p FROM PhotographerUniv p JOIN FETCH p.univ")
    List<PhotographerUniv> findByPhotographer_Id(Long photographerId);
}

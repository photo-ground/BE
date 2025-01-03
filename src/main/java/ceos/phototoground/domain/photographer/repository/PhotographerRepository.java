package ceos.phototoground.domain.photographer.repository;

import ceos.phototoground.domain.photographer.entity.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PhotographerRepository extends JpaRepository<Photographer, Long>, PhotographerRepositoryCustom {

}
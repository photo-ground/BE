package ceos.phototoground.photographer.repository;

import ceos.phototoground.photographer.domain.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PhotographerRepository extends JpaRepository<Photographer, Long>, PhotographerRepositoryCustom {

}
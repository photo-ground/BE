package ceos.phototoground.photographer.repository;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.photographer.domain.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Long> {
}
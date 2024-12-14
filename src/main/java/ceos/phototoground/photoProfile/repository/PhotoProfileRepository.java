package ceos.phototoground.photoProfile.repository;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PhotoProfileRepository extends JpaRepository<PhotoProfile, Long>, PhotoProfileRepositoryCustom {
    Optional<PhotoProfile> findByPhotographerId(Long photographerId);
}

package ceos.phototoground.domain.photoProfile.repository;

import ceos.phototoground.domain.photoProfile.entity.PhotoProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PhotoProfileRepository extends JpaRepository<PhotoProfile, Long>, PhotoProfileRepositoryCustom {
    Optional<PhotoProfile> findByPhotographer_Id(Long photographerId);
}

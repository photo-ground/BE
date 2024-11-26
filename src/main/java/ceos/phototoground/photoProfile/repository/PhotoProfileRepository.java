package ceos.phototoground.photoProfile.repository;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoProfileRepository extends JpaRepository<PhotoProfile, Long> {
}

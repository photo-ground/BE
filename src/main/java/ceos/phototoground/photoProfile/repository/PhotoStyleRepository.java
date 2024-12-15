package ceos.phototoground.photoProfile.repository;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.photoProfile.domain.PhotoStyle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoStyleRepository extends JpaRepository<PhotoStyle, Long> {
    
    List<PhotoStyle> findByPhotoProfile(PhotoProfile photoProfile);
}

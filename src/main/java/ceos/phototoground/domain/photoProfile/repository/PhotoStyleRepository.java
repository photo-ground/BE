package ceos.phototoground.domain.photoProfile.repository;

import ceos.phototoground.domain.photoProfile.entity.PhotoProfile;
import ceos.phototoground.domain.photoProfile.entity.PhotoStyle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoStyleRepository extends JpaRepository<PhotoStyle, Long> {
    
    List<PhotoStyle> findByPhotoProfile(PhotoProfile photoProfile);
}

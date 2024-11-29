package ceos.phototoground.postImage.repository;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.postImage.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}

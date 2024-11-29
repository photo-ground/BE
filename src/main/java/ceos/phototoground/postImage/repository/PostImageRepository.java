package ceos.phototoground.postImage.repository;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.postImage.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    @Query("SELECT pi.imageUrl FROM PostImage pi where pi.post.id = :postId")
    List<String> findImageUrlsByPost_Id(@Param("postId") Long postId);

    void deleteByPost_Id(Long postId);
}

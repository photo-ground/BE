package ceos.phototoground.postImage.repository;

import ceos.phototoground.postImage.domain.PostImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    @Query("SELECT pi.imageUrl FROM PostImage pi where pi.post.id = :postId")
    List<String> findImageUrlsByPost_Id(@Param("postId") Long postId);

    void deleteByPost_Id(Long postId);

    List<PostImage> findByPost_Id(Long postId);
}

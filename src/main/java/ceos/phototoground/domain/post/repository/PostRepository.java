package ceos.phototoground.domain.post.repository;

import ceos.phototoground.domain.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    List<Post> findByCreatedAtAfter(LocalDateTime aMonthAgo);
}

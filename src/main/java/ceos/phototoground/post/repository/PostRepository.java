package ceos.phototoground.post.repository;

import ceos.phototoground.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}

package ceos.phototoground.domain.post.repository;

import ceos.phototoground.domain.post.entity.Post;
import com.querydsl.core.Tuple;
import java.util.List;

public interface PostRepositoryCustom {
    
    List<Tuple> findPostsAndImagesByUnivWithNoOffset(String univName, Long cursor, int size);

    List<Post> findProfilePostWithNoOffset(Long photographerId, Long cursor, int i);
}

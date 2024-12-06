package ceos.phototoground.post.repository;

import com.querydsl.core.Tuple;
import java.util.List;

public interface PostRepositoryCustom {
    List<Tuple> findPostsAndImagesByUnivWithNoOffset(String univName, Long cursor, int size);
}

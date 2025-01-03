package ceos.phototoground.domain.postImage.repository;

import com.querydsl.core.Tuple;
import java.util.List;

public interface PostImageRepositoryCustom {

    List<Tuple> findBySpot_Id(Long spotId, Long cursor, int size);
}

package ceos.phototoground.domain.photographer.repository;

import com.querydsl.core.Tuple;
import java.util.List;

public interface PhotographerRepositoryCustom {
    List<Tuple> findPhotographerWithNoOffset(Long cursor, int size, String univ, String gender);
}

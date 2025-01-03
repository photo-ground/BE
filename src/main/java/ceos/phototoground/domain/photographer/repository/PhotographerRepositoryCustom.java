package ceos.phototoground.domain.photographer.repository;

import ceos.phototoground.domain.photographer.entity.Photographer;
import java.util.List;

public interface PhotographerRepositoryCustom {
    List<Photographer> findPhotographerWithNoOffset(Long cursor, int size, String univ, String gender);
}

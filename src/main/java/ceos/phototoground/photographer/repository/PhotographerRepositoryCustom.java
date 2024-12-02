package ceos.phototoground.photographer.repository;

import ceos.phototoground.photographer.domain.Photographer;
import java.util.List;

public interface PhotographerRepositoryCustom {
    List<Photographer> findPhotographerWithNoOffset(Long cursor, int size, String univ, String gender);
}

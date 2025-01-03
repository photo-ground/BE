package ceos.phototoground.domain.photoProfile.repository;

import com.querydsl.core.Tuple;
import java.util.List;

public interface PhotoProfileRepositoryCustom {

    List<Tuple> findByNicknameContains(String name, String cursor, int size);

    String generateNextCursor(List<Tuple> searchProfileList, String name);
}

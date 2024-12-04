package ceos.phototoground.photoProfile.service;

import ceos.phototoground.photoProfile.repository.PhotoProfileRepository;
import com.querydsl.core.Tuple;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoProfileService {

    private final PhotoProfileRepository photoProfileRepository;


    public List<Tuple> findByNicknameContains(String name, String cursor, int size) {

        return photoProfileRepository.findByNicknameContains(name, cursor, size);
    }

    public String generateNextCursor(List<Tuple> searchProfileList, String name) {

        return photoProfileRepository.generateNextCursor(searchProfileList, name);
    }
}

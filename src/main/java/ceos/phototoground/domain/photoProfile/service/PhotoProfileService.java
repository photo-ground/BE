package ceos.phototoground.domain.photoProfile.service;

import ceos.phototoground.domain.photoProfile.entity.PhotoProfile;
import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import ceos.phototoground.domain.photoProfile.repository.PhotoProfileRepository;
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

    public PhotoProfile findByPhotographer_Id(Long photographerId) {

        return photoProfileRepository.findByPhotographer_Id(photographerId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));
    }
}

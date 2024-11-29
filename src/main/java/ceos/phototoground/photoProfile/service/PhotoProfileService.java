package ceos.phototoground.photoProfile.service;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.photoProfile.repository.PhotoProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoProfileService {
    private final PhotoProfileRepository photoProfileRepository;

    public PhotoProfile findProfileByPhotographerId(Long photographerId) {
        return photoProfileRepository.findByPhotographerId(photographerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 작가 id의 프로필이 존재하지 않습니다."));
    }
}

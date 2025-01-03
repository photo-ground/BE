package ceos.phototoground.domain.photoProfile.service;

import ceos.phototoground.domain.photoProfile.repository.PhotoStyleRepository;
import ceos.phototoground.domain.photoProfile.entity.PhotoProfile;
import ceos.phototoground.domain.photoProfile.entity.PhotoStyle;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoStyleService {

    private final PhotoStyleRepository photoStyleRepository;

    public List<String> findByPhotoProfile(PhotoProfile photoProfile) {

        List<PhotoStyle> photoStyles = photoStyleRepository.findByPhotoProfile(photoProfile);

        return photoStyles.stream().map(photoStyle -> photoStyle.getStyle().getName()).toList();
    }
}

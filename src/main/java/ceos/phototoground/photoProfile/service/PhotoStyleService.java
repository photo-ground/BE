package ceos.phototoground.photoProfile.service;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.photoProfile.domain.PhotoStyle;
import ceos.phototoground.photoProfile.repository.PhotoStyleRepository;
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

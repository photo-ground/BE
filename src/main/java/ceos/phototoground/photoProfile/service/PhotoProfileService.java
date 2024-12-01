package ceos.phototoground.photoProfile.service;

import ceos.phototoground.photoProfile.repository.PhotoProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoProfileService {
    private final PhotoProfileRepository photoProfileRepository;


}

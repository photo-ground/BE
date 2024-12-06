package ceos.phototoground.photographer.service;

import ceos.phototoground.photographer.domain.Photographer;
import ceos.phototoground.photographer.repository.PhotographerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class PhotographerService {
    private final PhotographerRepository photographerRepository;

    @Transactional
    public Photographer findPhotographerById(Long photographerId){
        return photographerRepository.findById(photographerId).orElseThrow(()-> new IllegalArgumentException("해당 id의 사진작가는 존재하지 않습니다."));
    }
}

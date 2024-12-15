package ceos.phototoground.univ.service;

import ceos.phototoground.univ.domain.PhotographerUniv;
import ceos.phototoground.univ.repository.PhotographerUnivRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotographerUnivService {

    private final PhotographerUnivRepository photographerUnivRepository;

    public List<PhotographerUniv> findByPhotographer_Id(Long photographerId) {
        
        return photographerUnivRepository.findByPhotographer_Id(photographerId);
    }
}

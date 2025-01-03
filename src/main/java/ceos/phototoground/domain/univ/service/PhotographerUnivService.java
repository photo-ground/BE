package ceos.phototoground.domain.univ.service;

import ceos.phototoground.domain.univ.entity.PhotographerUniv;
import ceos.phototoground.domain.univ.repository.PhotographerUnivRepository;
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

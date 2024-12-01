package ceos.phototoground.photographer.service;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.photographer.domain.Photographer;
import ceos.phototoground.photographer.dto.PhotographerListDTO;
import ceos.phototoground.photographer.dto.PhotographerResponseDTO;
import ceos.phototoground.photographer.repository.PhotographerRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotographerService {

    private final PhotographerRepository photographerRepository;

    @Transactional
    public Photographer findPhotographerById(Long photographerId) {
        return photographerRepository.findById(photographerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 사진작가는 존재하지 않습니다."));
    }


    public PhotographerListDTO getPhotographerList(Long cursor, int size, String univ, String gender) {

        List<Photographer> photographerList = photographerRepository.findPhotographerWithNoOffset(cursor, size + 1,
                univ, gender);

        boolean hasNext = photographerList.size() > size;

        if (hasNext) {
            photographerList = photographerList.subList(0, size);
        }

        List<PhotographerResponseDTO> dtos = new ArrayList<>();

        for (Photographer photographer : photographerList) {
            PhotoProfile photoProfile = photographer.getPhotoProfile();
            PhotographerResponseDTO dto = PhotographerResponseDTO.of(photographer, photoProfile);
            dtos.add(dto);
        }

        return PhotographerListDTO.of(dtos, hasNext);

    }


}

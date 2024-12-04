package ceos.phototoground.photographer.service;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.photoProfile.domain.QPhotoProfile;
import ceos.phototoground.photoProfile.service.PhotoProfileService;
import ceos.phototoground.photographer.domain.Photographer;
import ceos.phototoground.photographer.domain.QPhotographer;
import ceos.phototoground.photographer.dto.PhotographerIntroDTO;
import ceos.phototoground.photographer.dto.PhotographerListDTO;
import ceos.phototoground.photographer.dto.PhotographerResponseDTO;
import ceos.phototoground.photographer.dto.PhotographerSearchListDTO;
import ceos.phototoground.photographer.repository.PhotographerRepository;
import ceos.phototoground.univ.domain.PhotographerUniv;
import ceos.phototoground.univ.service.PhotographerUnivService;
import com.querydsl.core.Tuple;
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
    private final PhotoProfileService photoProfileService;
    private final PhotographerUnivService photographerUnivService;

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


    public PhotographerSearchListDTO searchPhotographer(String name, String cursor, int size) {

        List<Tuple> searchProfileList = photoProfileService.findByNicknameContains(name, cursor, size + 1);

        boolean hasNext = searchProfileList.size() > size;

        if (hasNext) {
            searchProfileList = searchProfileList.subList(0, size);
        }

        // 마지막으로 반환된 searchProfileList 커서값 반환 (priority+id) -> 다음 요청에 사용될 커서값
        String nextCursor = photoProfileService.generateNextCursor(searchProfileList, name);

        List<PhotographerResponseDTO> dtos = new ArrayList<>();

        for (Tuple tuple : searchProfileList) {
            //Photographer photographer= photographerRepository.findById(profile.getPhotographer().getId()).orElseThrow(("해당 프로필에 속하는 작가가 존재하지 않습니다.");
            Photographer photographer = tuple.get(QPhotographer.photographer);
            PhotoProfile profile = tuple.get(QPhotoProfile.photoProfile);

            PhotographerResponseDTO dto = PhotographerResponseDTO.of(photographer, profile);
            dtos.add(dto);
        }

        return PhotographerSearchListDTO.of(dtos, hasNext, nextCursor);

    }

    public PhotographerIntroDTO getPhotographerIntro(Long photographerId) {

        Photographer photographer = photographerRepository.findById(photographerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 작가가 존재하지 않습니다."));

        PhotoProfile photoProfile = photographer.getPhotoProfile();

        List<PhotographerUniv> photographerUnivList = photographerUnivService.findByPhotographer_Id(photographerId);

        List<String> univNameList = photographerUnivList.stream()
                .map(photographerUniv -> photographerUniv.getUniv().getName())
                .toList();

        return PhotographerIntroDTO.of(photographer, photoProfile, univNameList);
    }
}

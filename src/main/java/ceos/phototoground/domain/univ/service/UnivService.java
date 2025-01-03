package ceos.phototoground.domain.univ.service;


import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import ceos.phototoground.domain.univ.entity.PhotographerUniv;
import ceos.phototoground.domain.univ.entity.Univ;
import ceos.phototoground.domain.univ.repository.UnivRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UnivService {
    private final UnivRepository univRepository;

    public Univ findUnivById(Long univId) {
        return univRepository.findById(univId).orElseThrow(() -> new IllegalArgumentException("해당 id의 대학은 존재하지 않습니다."));
    }

    public Univ findByName(String univ) {
        return univRepository.findByName(univ).orElseThrow(() -> new CustomException(ErrorCode.UNIV_NOT_FOUND));
    }

    public List<String> findByPhotographerUnivList(List<PhotographerUniv> photographerUnivList) {

        return photographerUnivList.stream()
                .map(list -> list.getUniv().getName())
                .toList();
    }
}

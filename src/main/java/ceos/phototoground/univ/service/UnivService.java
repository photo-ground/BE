package ceos.phototoground.univ.service;


import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import ceos.phototoground.univ.domain.Univ;
import ceos.phototoground.univ.repository.UnivRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UnivService {
    private final UnivRepository univRepository;

    @Transactional
    public Univ findUnivById(Long univId) {
        return univRepository.findById(univId).orElseThrow(() -> new IllegalArgumentException("해당 id의 대학은 존재하지 않습니다."));
    }

    public Univ findByName(String univ) {
        return univRepository.findByName(univ).orElseThrow(() -> new CustomException(ErrorCode.UNIV_NOT_FOUND));
    }
}

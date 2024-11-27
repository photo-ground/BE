package ceos.phototoground.univ.service;


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
    public Univ findUnivById(Long univId){
        return univRepository.findById(univId).orElseThrow(()-> new IllegalArgumentException("해당 id의 대학은 존재하지 않습니다."));
    }
}

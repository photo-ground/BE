package ceos.phototoground.spot.service;

import ceos.phototoground.spot.domain.Spot;
import ceos.phototoground.spot.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotService {
    private final SpotRepository spotRepository;

    @Transactional
    public List<Spot> findSpotsById(List<Long> spotIds){
        //중복 제거된 spot 리스트 가져오기
        List<Spot> spotNoOverlap = spotRepository.findByIdIn(spotIds);

        //key:id value:spot으로 하는 map 생성
        Map<Long, Spot> map = spotNoOverlap.stream()
                .collect(Collectors.toMap(Spot::getId, spot -> spot));

        //spotIds 리스트 돌며 해당하는 id의 spot 모아 리스트 생성
        return spotIds.stream()
                .map(spotId -> {
                    Spot spot = map.get(spotId);

                    if(spot==null){
                        throw new IllegalArgumentException(spotId + "id의 스팟은 존재하지 않습니다.");
                    }
                    return spot;
                })
                .toList();
    }
}

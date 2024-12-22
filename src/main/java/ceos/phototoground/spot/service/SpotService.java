package ceos.phototoground.spot.service;

import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.photoProfile.domain.QPhotoProfile;
import ceos.phototoground.post.domain.Post;
import ceos.phototoground.post.domain.QPost;
import ceos.phototoground.postImage.domain.PostImage;
import ceos.phototoground.postImage.domain.QPostImage;
import ceos.phototoground.postImage.dto.SpotPostImage;
import ceos.phototoground.postImage.dto.SpotPostImageList;
import ceos.phototoground.postImage.repository.PostImageRepository;
import ceos.phototoground.spot.domain.Spot;
import ceos.phototoground.spot.dto.OneSpotInfo;
import ceos.phototoground.spot.dto.SpotInfo;
import ceos.phototoground.spot.repository.SpotRepository;
import ceos.phototoground.univ.domain.Univ;
import ceos.phototoground.univ.service.UnivService;
import com.querydsl.core.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotService {

    private final SpotRepository spotRepository;
    private final UnivService univService;
    private final PostImageRepository postImageRepository;

    @Transactional
    public List<Spot> findSpotsById(List<Long> spotIds) {
        //중복 제거된 spot 리스트 가져오기
        List<Spot> spotNoOverlap = spotRepository.findByIdIn(spotIds);

        //key:id value:spot으로 하는 map 생성
        Map<Long, Spot> map = spotNoOverlap.stream()
                .collect(Collectors.toMap(Spot::getId, spot -> spot));

        //spotIds 리스트 돌며 해당하는 id의 spot 모아 리스트 생성
        return spotIds.stream()
                .map(spotId -> {
                    Spot spot = map.get(spotId);

                    if (spot == null) {
                        throw new IllegalArgumentException(spotId + "id의 스팟은 존재하지 않습니다.");
                    }
                    return spot;
                })
                .toList();
    }


    public List<SpotInfo> showSpotInfoList(String univ) {

        Univ university = univService.findByName(univ);
        List<Spot> spots = spotRepository.findByUniv_Id(university.getId());

        return spots.stream()
                .map(SpotInfo::from)
                .toList();
    }


    public OneSpotInfo showOneSpotInfo(Long spotId, Long cursor, int size) {

        // 스팟 정보 불러오기 (id, name, content)
        Spot spot = spotRepository.findById(spotId).orElseThrow(() -> new CustomException(ErrorCode.SPOT_NOT_FOUND));

        // 해당 스팟에서 찍힌 이미지 가져오기 (페이징 적용)
        List<Tuple> tuples = postImageRepository.findBySpot_Id(spot.getId(), cursor, size + 1);

        boolean hasNext = tuples.size() > size;

        if (hasNext) {
            tuples = tuples.subList(0, size);
        }

        List<SpotPostImage> dtos = new ArrayList<>();

        for (Tuple tuple : tuples) {
            PostImage postImage = tuple.get(QPostImage.postImage);
            Post post = tuple.get(QPost.post);
            PhotoProfile profile = tuple.get(QPhotoProfile.photoProfile);

            dtos.add(SpotPostImage.of(postImage, post, profile));
        }

        SpotPostImageList imageList = SpotPostImageList.of(dtos, hasNext);

        return OneSpotInfo.of(spot, imageList);
    }

}

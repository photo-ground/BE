package ceos.phototoground.domain.photographer.service;

import ceos.phototoground.domain.customer.dto.CustomUserDetails;
import ceos.phototoground.domain.follow.entity.Follow;
import ceos.phototoground.domain.follow.service.FollowService;
import ceos.phototoground.domain.photoProfile.entity.PhotoProfile;
import ceos.phototoground.domain.photoProfile.entity.QPhotoProfile;
import ceos.phototoground.domain.photoProfile.service.PhotoProfileService;
import ceos.phototoground.domain.photoProfile.service.PhotoStyleService;
import ceos.phototoground.domain.photographer.dto.PhotographerBottomDTO;
import ceos.phototoground.domain.photographer.dto.PhotographerIntroDTO;
import ceos.phototoground.domain.photographer.dto.PhotographerListDTO;
import ceos.phototoground.domain.photographer.dto.PhotographerResponseDTO;
import ceos.phototoground.domain.photographer.dto.PhotographerSearchListDTO;
import ceos.phototoground.domain.photographer.entity.Photographer;
import ceos.phototoground.domain.photographer.entity.QPhotographer;
import ceos.phototoground.domain.photographer.repository.PhotographerRepository;
import ceos.phototoground.domain.post.dto.ProfilePostResponseListDTO;
import ceos.phototoground.domain.post.entity.Post;
import ceos.phototoground.domain.post.service.PostService;
import ceos.phototoground.domain.univ.entity.PhotographerUniv;
import ceos.phototoground.domain.univ.service.PhotographerUnivService;
import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import com.querydsl.core.Tuple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final PhotoStyleService photoStyleService;
    private final PostService postService;
    private final FollowService followService;


    @Transactional
    public Photographer findPhotographerById(Long photographerId) {
        return photographerRepository.findById(photographerId)
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));
    }


    public PhotographerListDTO getPhotographerList(Long cursor, int size, String univ, String gender) {

        System.out.println("서비스 첫 size : " + size);
        List<Photographer> photographerList = photographerRepository.findPhotographerWithNoOffset(cursor, size + 1,
                univ, gender);

        System.out.println("photographerList size : " + photographerList.size());
        boolean hasNext = photographerList.size() > size;

        if (hasNext) {
            photographerList = photographerList.subList(0, size);
        }
        System.out.println("hasNext 후 photographerList size : " + photographerList.size());
        System.out.println("hasNext 불린 값 : " + hasNext);

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

        String nextCursor = null;
        boolean hasNext = searchProfileList.size() > size;

        if (hasNext) {
            searchProfileList = searchProfileList.subList(0, size);

            // 마지막으로 반환된 searchProfileList 커서값 반환 (priority+id) -> 다음 요청에 사용될 커서값
            nextCursor = photoProfileService.generateNextCursor(searchProfileList, name);
        }

        List<PhotographerResponseDTO> dtos = new ArrayList<>();

        for (Tuple tuple : searchProfileList) {
            Photographer photographer = tuple.get(QPhotographer.photographer);
            PhotoProfile profile = tuple.get(QPhotoProfile.photoProfile);

            PhotographerResponseDTO dto = PhotographerResponseDTO.of(photographer, profile);
            dtos.add(dto);
        }

        return PhotographerSearchListDTO.of(dtos, hasNext, nextCursor);

    }


    public PhotographerIntroDTO getPhotographerIntro(Long photographerId, CustomUserDetails customUserDetails) {

        // 로그인 안 한 사용자
        boolean isFollowing = false;

        // 로그인 한 사용자
        if (customUserDetails != null) {
            Long customerId = customUserDetails.getCustomer().getId();
            Follow follow = followService.findByCustomerAndPhotographer_Id(customerId, photographerId);
            isFollowing = follow != null;
        }

        Photographer photographer = photographerRepository.findById(photographerId)
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));

        PhotoProfile photoProfile = photographer.getPhotoProfile();

        List<PhotographerUniv> photographerUnivList = photographerUnivService.findByPhotographer_Id(photographerId);

        List<String> univNameList = photographerUnivList.stream()
                .map(photographerUniv -> photographerUniv.getUniv().getName())
                .toList();

        List<String> styleList = photoStyleService.findByPhotoProfile(photoProfile);

        return PhotographerIntroDTO.of(photographer, photoProfile, univNameList, styleList, isFollowing);
    }


    public PhotographerBottomDTO getPhotographerBottom(Long photographerId, Long cursor, int size) {

        Photographer photographer = photographerRepository.findById(photographerId)
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));

        ProfilePostResponseListDTO postList = postService.findProfilePostWithNoOffset(photographerId, cursor, size);

        return PhotographerBottomDTO.from(postList);
    }

    // 활발히 활동하는 작가 리스트 져오기
    // 최근 한달이내 작성된 게시글만 모두 가져온 후 각 작가 id를 map에 저장 -> value 가장 큰 작가 8명 가져오기
    public List<PhotographerResponseDTO> getActivePhotographer() {

        // 최근 한달이내 작성된 게시글만 모두 가져오기
        List<Post> posts = postService.getRecentPosts();

        Map<Long, Long> map = new HashMap<>();

        // 각 작가 id를 map에 저장
        posts.stream()
                .map(post -> post.getPhotographer().getId())
                .forEach(photographerId -> map.put(photographerId, map.getOrDefault(photographerId, 0L) + 1L));

        // 가장 개수 많은 작가 8명 가져오기
        List<Long> photographerIds = map.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(8) // 8명
                .map(Map.Entry::getKey)
                .toList();

        List<PhotographerResponseDTO> dtos = photographerIds.stream()
                .map(id -> {
                    Photographer photographer = photographerRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("해당 id의 작가는 존재하지 않습니다."));
                    PhotoProfile photoProfile = photographer.getPhotoProfile();

                    return PhotographerResponseDTO.of(photographer, photoProfile);
                })
                .toList();

        return dtos;
    }

    public Photographer findById(Long photographerId) {
        return photographerRepository.findById(photographerId)
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));
    }
}

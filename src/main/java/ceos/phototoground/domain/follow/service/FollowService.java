package ceos.phototoground.domain.follow.service;

import ceos.phototoground.domain.customer.entity.Customer;
import ceos.phototoground.domain.follow.dto.FollowResponseDto;
import ceos.phototoground.domain.follow.entity.Follow;
import ceos.phototoground.domain.follow.repository.FollowRepository;
import ceos.phototoground.domain.photographer.entity.Photographer;
import ceos.phototoground.domain.photographer.repository.PhotographerRepository;
import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {

    private final FollowRepository followRepository;
    private final PhotographerRepository photographerRepository;

    public void followPhotographer(Customer customer, Long photographerId) {
        // 작가 정보 가져오기
        Photographer photographer = photographerRepository.findById(photographerId)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.PHOTOGRAPHER_NOT_FOUND
                ));

        // 이미 팔로우한 경우 중복 방지
        boolean alreadyFollowed = followRepository.existsByCustomerAndPhotographer(customer, photographer);
        if (alreadyFollowed) {
            throw new CustomException(
                    ErrorCode.ALREADY_FOLLOWING);
        }

        // 팔로우 생성 및 저장
        Follow follow = Follow.builder()
                .customer(customer)
                .photographer(photographer)
                .build();

        followRepository.save(follow);
    }


    public void unfollowPhotographer(Customer customer, Long photographerId) {
        // 작가 정보 가져오기
        Photographer photographer = photographerRepository.findById(photographerId)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.PHOTOGRAPHER_NOT_FOUND));

        // 팔로우 관계 확인
        Follow follow = followRepository.findByCustomerAndPhotographer(customer, photographer)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.FOLLOW_RELATIONSHIP_NOT_FOUND));

        // 팔로우 취소
        followRepository.delete(follow);
    }

    public List<FollowResponseDto> getAllFollowedPhotographers(Customer customer) {
        // 팔로우 관계에서 Photographer 리스트 추출
        return followRepository.findByCustomer(customer)
                .stream()
                .map(Follow::getPhotographer)
                .map(FollowResponseDto::from)
                .collect(Collectors.toList());
    }

    public Follow findByCustomerAndPhotographer_Id(Long customerId, Long photographerId) {

        return followRepository.findByCustomer_IdAndPhotographer_Id(customerId, photographerId);
    }
}

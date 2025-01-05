package ceos.phototoground.domain.review.service;

import ceos.phototoground.domain.photographer.entity.Photographer;
import ceos.phototoground.domain.photographer.repository.PhotographerRepository;
import ceos.phototoground.domain.reservation.entity.Reservation;
import ceos.phototoground.domain.reservation.repository.ReservationRepository;
import ceos.phototoground.domain.review.dto.PhotographerReviewsResponseDto;
import ceos.phototoground.domain.review.dto.ReviewRequestDto;
import ceos.phototoground.domain.review.dto.ReviewResponseDto;
import ceos.phototoground.domain.review.entity.Review;
import ceos.phototoground.domain.review.repository.ReviewRepository;
import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final PhotographerRepository photographerRepository;

    // 리뷰 작성
    @Transactional
    public ReviewResponseDto createReview(Long customerId, Long reservationId, ReviewRequestDto reviewRequestDto) {
        // 예약 정보 조회
        Reservation reservation = reservationRepository.findById(reservationId)
                                                       .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        // 예약의 고객 ID와 현재 로그인된 고객 ID 일치 여부 확인
        if (!reservation.getCustomer().getId().equals(customerId)) {
            throw new CustomException(ErrorCode.REVIEW_PERMISSION_DENIED);
        }

        // 이미 리뷰 작성 완료 여부 확인
        if (reservation.isReviewComplete()) {
            throw new CustomException(ErrorCode.REVIEW_ALREADY_COMPLETED);
        }

        // 리뷰 생성 및 저장
        Review review = Review.builder()
                              .content(reviewRequestDto.getContent())
                              .score(reviewRequestDto.getScore())
                              .customer(reservation.getCustomer())
                              .reservation(reservation)
                              .build();

        reviewRepository.save(review);

        // 예약 상태 업데이트 (리뷰 완료)
        reservation.completeReview();

        // 응답 DTO로 변환
        return ReviewResponseDto.fromEntity(review);
    }

    // 작가의 리뷰 전체 조회
    public PhotographerReviewsResponseDto getPhotographerReviews(Long photographerId) {
        // 작가 정보 확인
        Photographer photographer = photographerRepository.findById(photographerId)
                                                          .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));

        // 작가에 대한 모든 리뷰 조회 (최신 순 정렬)
        List<Review> reviews = reviewRepository.findByReservationPhotographerIdOrderByCreatedAtDesc(photographerId);

        // 리뷰 개수와 평균 점수 계산
        int count = reviews.size();
        double averageScore = reviews.stream()
                                     .mapToInt(Review::getScore)
                                     .average()
                                     .orElse(0.0);

        // 리뷰 목록 변환
        List<PhotographerReviewsResponseDto.ReviewDetailDto> reviewDetails = reviews.stream()
                                                                                    .map(PhotographerReviewsResponseDto.ReviewDetailDto::fromEntity)
                                                                                    .toList();

        // 응답 DTO 생성
        return PhotographerReviewsResponseDto.builder()
                                             .count(count)
                                             .averageScore(averageScore)
                                             .reviews(reviewDetails)
                                             .build();
    }
}

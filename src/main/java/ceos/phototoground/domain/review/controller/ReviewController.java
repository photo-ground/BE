package ceos.phototoground.domain.review.controller;

import ceos.phototoground.domain.customer.dto.CustomUserDetails;
import ceos.phototoground.domain.review.dto.PhotographerReviewsResponseDto;
import ceos.phototoground.domain.review.dto.ReviewRequestDto;
import ceos.phototoground.domain.review.dto.ReviewResponseDto;
import ceos.phototoground.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/review/{reservationId}")
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable Long reservationId,
            @RequestBody @Valid ReviewRequestDto reviewRequestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 로그인된 사용자 정보 가져오기
        Long customerId = userDetails.getCustomer().getId();

        // 리뷰 작성
        ReviewResponseDto responseDto = reviewService.createReview(customerId, reservationId, reviewRequestDto);

        return ResponseEntity.ok(responseDto);
    }

    // 고객의 모든 리뷰 조회
    @GetMapping("/review")
    public ResponseEntity<PhotographerReviewsResponseDto> getCustomerReviews(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 로그인된 고객의 ID 가져오기
        Long customerId = userDetails.getCustomer().getId();

        // 고객 리뷰 조회
        PhotographerReviewsResponseDto responseDto = reviewService.getCustomerReviews(customerId);

        return ResponseEntity.ok(responseDto);
    }

    // 리뷰 단건 조회
    @GetMapping("review/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable Long reviewId) {
        ReviewResponseDto responseDto = reviewService.getReviewById(reviewId);

        return ResponseEntity.ok(responseDto);
    }

    // 작가의 모든 리뷰 조회
    @GetMapping("/photographer/{photographerId}/review")
    public ResponseEntity<PhotographerReviewsResponseDto> getPhotographerReviews(
            @PathVariable Long photographerId) {
        PhotographerReviewsResponseDto responseDto = reviewService.getPhotographerReviews(photographerId);

        return ResponseEntity.ok(responseDto);
    }
}

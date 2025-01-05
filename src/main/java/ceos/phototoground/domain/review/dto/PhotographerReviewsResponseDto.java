package ceos.phototoground.domain.review.dto;

import ceos.phototoground.domain.review.entity.Review;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotographerReviewsResponseDto {
    private int count; // 리뷰 개수
    private double averageScore; // 평균 점수
    private List<ReviewDetailDto> reviews; // 리뷰 상세 목록

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReviewDetailDto {
        private Long reviewId;
        private LocalDateTime createdAt;
        private String content;

        public static ReviewDetailDto fromEntity(Review review) {
            return ReviewDetailDto.builder()
                                  .reviewId(review.getId())
                                  .createdAt(review.getCreatedAt())
                                  .content(review.getContent())
                                  .build();
        }
    }
}

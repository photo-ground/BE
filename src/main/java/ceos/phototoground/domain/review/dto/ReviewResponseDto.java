package ceos.phototoground.domain.review.dto;

import ceos.phototoground.domain.review.entity.Review;
import java.time.LocalDateTime;
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
public class ReviewResponseDto {
    private Long reviewId;
    private Long reservationId;
    private String photographerName;
    private String content;
    private int score;
    private LocalDateTime createdAt;

    public static ReviewResponseDto fromEntity(Review review) {
        return ReviewResponseDto.builder()
                                .reviewId(review.getId())
                                .reservationId(review.getReservation()
                                                     .getId())
                                .photographerName(review.getReservation()
                                                        .getPhotographer()
                                                        .getPhotoProfile()
                                                        .getNickname())
                                .content(review.getContent())
                                .score(review.getScore())
                                .createdAt(review.getCreatedAt())
                                .build();
    }
}

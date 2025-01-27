package ceos.phototoground.domain.review.repository;

import ceos.phototoground.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 작가에게 작성된 리뷰를 최신순으로 조회
    List<Review> findByReservationPhotographerIdOrderByCreatedAtDesc(Long photographerId);

    // 특정 고객이 작성한 리뷰를 최신순으로 조회
    List<Review> findByCustomerIdOrderByCreatedAtDesc(Long customerId);

    boolean existsByCustomerIdAndReservationId(Long customerId, Long reservationId);
}

package ceos.phototoground.domain.review.repository;

import ceos.phototoground.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReservationPhotographerIdOrderByCreatedAtDesc(Long photographerId);
}

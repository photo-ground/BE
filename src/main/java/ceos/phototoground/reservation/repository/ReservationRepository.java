package ceos.phototoground.reservation.repository;

import ceos.phototoground.photoProfile.domain.PhotoProfile;
import ceos.phototoground.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}

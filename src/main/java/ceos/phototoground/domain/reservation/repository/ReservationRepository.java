package ceos.phototoground.domain.reservation.repository;

import ceos.phototoground.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByCustomer_Id(Long payerId);
}

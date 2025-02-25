package ceos.phototoground.domain.reservation.repository;

import ceos.phototoground.domain.reservation.entity.Reservation;
import ceos.phototoground.domain.reservation.entity.Status;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByCustomer_Id(Long payerId);

    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :customerId " +
            "AND FUNCTION('YEAR', r.date) = :year " +
            "AND FUNCTION('MONTH', r.date) = :month " +
            "AND r.date >= :today")
    List<Reservation> findByCustomer_IdAndYearAndMonthAfterToday(@Param("customerId") Long customerId,
                                                                 @Param("year") int year,
                                                                 @Param("month") int month,
                                                                 @Param("today") LocalDate today);

    @Query("SELECT r FROM Reservation r WHERE r.customer.id= :customerId " +
            "AND r.date>=:today")
    List<Reservation> findByCustomer_IdAfterToday(@Param("customerId") Long customerId,
                                                  @Param("today") LocalDate today);


    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :customerId " +
            "AND r.status <> :status")
    List<Reservation> findByCustomer_IdAndStatusNot(@Param("customerId") Long customerId,
                                                    @Param("status") Status status);


    List<Reservation> findByCustomer_IdAndStatus(Long customerId, Status status);

    List<Reservation> findByDate(LocalDate now);
}

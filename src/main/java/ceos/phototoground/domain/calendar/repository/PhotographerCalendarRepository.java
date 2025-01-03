package ceos.phototoground.domain.calendar.repository;

import ceos.phototoground.domain.calendar.entity.PhotographerCalendar;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PhotographerCalendarRepository extends JpaRepository<PhotographerCalendar, Long> {

    @Query("SELECT pc FROM PhotographerCalendar pc JOIN FETCH pc.calendar")
    List<PhotographerCalendar> findByPhotographer_Id(Long photographerId);

    @Query("SELECT pc FROM PhotographerCalendar pc "
            + "JOIN FETCH pc.calendar c "
            + "WHERE pc.photographer.id=:photographerId "
            + "AND c.date BETWEEN :now AND :endDate")
    List<PhotographerCalendar> findByPhotographer_IdAndDateBetween(@Param("photographerId") Long photographerId,
                                                                   @Param("now") LocalDate now,
                                                                   @Param("endDate") LocalDate endDate);
}

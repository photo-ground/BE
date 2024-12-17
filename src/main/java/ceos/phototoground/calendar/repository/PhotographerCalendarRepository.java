package ceos.phototoground.calendar.repository;

import ceos.phototoground.calendar.domain.PhotographerCalendar;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PhotographerCalendarRepository extends JpaRepository<PhotographerCalendar, Long> {

    @Query("SELECT pc FROM PhotographerCalendar pc JOIN FETCH pc.calendar")
    List<PhotographerCalendar> findByPhotographer_Id(Long photographerId);
}

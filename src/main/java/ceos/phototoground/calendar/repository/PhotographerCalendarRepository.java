package ceos.phototoground.calendar.repository;

import ceos.phototoground.calendar.domain.PhotographerCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotographerCalendarRepository extends JpaRepository<PhotographerCalendar, Long> {
}

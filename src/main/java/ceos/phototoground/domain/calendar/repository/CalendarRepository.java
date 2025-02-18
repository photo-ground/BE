package ceos.phototoground.domain.calendar.repository;

import ceos.phototoground.domain.calendar.entity.Calendar;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    List<Calendar> findAllByIdIn(List<Long> ids);

    void deleteByDateBefore(LocalDate oneMonthAgo);
}

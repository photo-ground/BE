package ceos.phototoground.schedule.repository;

import ceos.phototoground.schedule.domain.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByPhotographer_Id(Long photographerId);
}

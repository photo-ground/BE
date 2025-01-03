package ceos.phototoground.domain.schedule.repository;

import ceos.phototoground.domain.schedule.entity.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByPhotographer_Id(Long photographerId);
}

package ceos.phototoground.domain.schedule.service;

import ceos.phototoground.domain.schedule.dto.WeekDaySchedule;
import ceos.phototoground.domain.schedule.entity.Schedule;
import ceos.phototoground.domain.schedule.repository.ScheduleRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public List<Schedule> findByPhotographer_Id(Long photographerId) {
        return scheduleRepository.findByPhotographer_Id(photographerId);
    }

    public List<WeekDaySchedule> getWeekDaySchedules(List<Schedule> schedule) {

        // 요일별로 그룹화하고 시간대 리스트를 묶음
        Map<String, List<Integer>> groupedSchedules = schedule.stream()
                .collect(Collectors.groupingBy(
                        sch -> sch.getWeekday().name(),               // 그룹화 기준(요일)
                        Collectors.mapping(                           // 그룹화된 각 항목을 매핑하여 원하는 값을 추출
                                sch -> sch.getTimeSlot().getHour(),   //어떤 값을 매핑할 지
                                Collectors.toList()
                        )
                ));

        // 그룹화된 데이터를 WeekDaySchedule 객체로 변환
        return groupedSchedules.entrySet().stream()
                .map(entry -> WeekDaySchedule.of(entry.getKey(), entry.getValue()))
                .toList();
    }
}

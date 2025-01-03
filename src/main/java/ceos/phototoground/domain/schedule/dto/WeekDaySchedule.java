package ceos.phototoground.domain.schedule.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeekDaySchedule {

    private String weekDay;
    private List<Integer> timeSlot;

    public static WeekDaySchedule of(String weekDay, List<Integer> timeSlot) {
        return WeekDaySchedule.builder()
                .weekDay(weekDay)
                .timeSlot(timeSlot)
                .build();
    }
}

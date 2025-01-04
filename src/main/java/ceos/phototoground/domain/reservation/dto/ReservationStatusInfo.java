package ceos.phototoground.domain.reservation.dto;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationStatusInfo {

    private List<String> reserveDates;
    private DateScheduleDTO upcomingSchedule;
    private List<DateScheduleDTO> dateSchedules;

    public static ReservationStatusInfo from(List<DateScheduleDTO> dateSchedules) {

        List<String> dates = dateSchedules.stream().map(DateScheduleDTO::getDate).toList();

        // dateSchedules에서 가장 빠른 날짜 찾기
        Optional<DateScheduleDTO> earliestSchedule = dateSchedules.stream()
                .min(Comparator.comparing(DateScheduleDTO::getDate));

        return ReservationStatusInfo.builder()
                .reserveDates(dates)
                .upcomingSchedule(earliestSchedule.orElse(null))
                .dateSchedules(dateSchedules)
                .build();
    }
}

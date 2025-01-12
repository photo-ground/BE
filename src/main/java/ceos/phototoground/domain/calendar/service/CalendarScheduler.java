package ceos.phototoground.domain.calendar.service;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalendarScheduler {

    private final CalendarService calendarService;

    @Scheduled(cron = "0 0 3 * * *") // 매일 새벽 3시 실행
    public void deleteOldCalendarData() {
        calendarService.deleteOldData(); // 한 달 이상 지난 날짜 삭제
    }
}

package ceos.phototoground.domain.calendar.service;

import ceos.phototoground.domain.calendar.entity.Calendar;
import ceos.phototoground.domain.calendar.entity.PhotographerCalendar;
import ceos.phototoground.domain.calendar.repository.CalendarRepository;
import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public Calendar findById(Long calendarId) {

        return calendarRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(ErrorCode.CALENDAR_NOT_FOUND));
    }

    public List<String> findByIdIn(List<PhotographerCalendar> photoCalendar) {

        List<Long> ids = photoCalendar.stream()
                .map(pc -> pc.getCalendar().getId())
                .toList();

        List<Calendar> calendarList = calendarRepository.findAllByIdIn(ids);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return calendarList.stream()
                .map(li -> li.getDate().format(formatter))
                .toList();
    }
}

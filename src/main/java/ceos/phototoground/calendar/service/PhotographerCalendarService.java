package ceos.phototoground.calendar.service;

import ceos.phototoground.calendar.domain.PhotographerCalendar;
import ceos.phototoground.calendar.repository.PhotographerCalendarRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotographerCalendarService {

    private final PhotographerCalendarRepository photographerCalendarRepository;

    public List<PhotographerCalendar> findByPhotographer_Id(Long photographerId) {
        
        return photographerCalendarRepository.findByPhotographer_Id(photographerId);
    }
}

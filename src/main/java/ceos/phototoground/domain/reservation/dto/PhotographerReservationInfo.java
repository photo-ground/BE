package ceos.phototoground.domain.reservation.dto;

import ceos.phototoground.domain.photoProfile.entity.PhotoProfile;
import ceos.phototoground.domain.schedule.dto.WeekDaySchedule;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

// 예약신청 페이지 조회
@Getter
@Builder
public class PhotographerReservationInfo {

    private final String nickname;
    private final Long price;
    private final Long addPrice;
    private final List<String> availableUniv;
    private final List<String> availableDate;
    private final List<WeekDaySchedule> schedule;

    public static PhotographerReservationInfo of(PhotoProfile profile, List<WeekDaySchedule> weekDaySchedule,
                                                 List<String> availableDate,
                                                 List<String> univName) {
        return PhotographerReservationInfo.builder()
                .nickname(profile.getNickname())
                .price(profile.getPrice())
                .addPrice(profile.getAddPrice())
                .availableUniv(univName)
                .availableDate(availableDate)
                .schedule(weekDaySchedule)
                .build();
    }
}

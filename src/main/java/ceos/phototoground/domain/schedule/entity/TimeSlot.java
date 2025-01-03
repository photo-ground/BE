package ceos.phototoground.domain.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeSlot {
    HOUR_0(0),
    HOUR_1(1),
    HOUR_2(2),
    HOUR_3(3),
    HOUR_4(4),
    HOUR_5(5),
    HOUR_6(6),
    HOUR_7(7),
    HOUR_8(8),
    HOUR_9(9),
    HOUR_10(10),
    HOUR_11(11),
    HOUR_12(12),
    HOUR_13(13),
    HOUR_14(14),
    HOUR_15(15),
    HOUR_16(16),
    HOUR_17(17),
    HOUR_18(18),
    HOUR_19(19),
    HOUR_20(20),
    HOUR_21(21),
    HOUR_22(22),
    HOUR_23(23),
    HOUR_24(24);

    private final int hour; // 정수 값으로 시간 저장

    // 정수 값을 통해 TimeSlot 검색
    public static TimeSlot fromHour(int hour) {
        for (TimeSlot timeSlot : values()) {
            if (timeSlot.hour == hour) {
                return timeSlot;
            }
        }
        throw new IllegalArgumentException("Invalid hour: " + hour);
    }
}

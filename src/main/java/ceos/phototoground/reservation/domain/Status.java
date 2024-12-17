package ceos.phototoground.reservation.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {

    CANCELED("예약취소"),
    PENDING("예약대기"),
    PAYMENT_PENDING("결제대기"),
    PAYMENT_CONFIRMED("결제확인"),
    PAYMENT_ERROR("결제오류"),
    CONFIRMED("예약확정"),
    COMPLETED("촬영완료");

    private final String name; //한국어 이름
}

package ceos.phototoground.domain.reservation.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {

    CANCELED("예약취소"),
    PENDING("예약신청"),  // 고객이 예약을 신청했지만 승인되진 않은 상태
    PAYMENT_PENDING("결제대기"), // 고객이 결제하기 전
    PAYMENT_CONFIRMED("결제확인"), // 고객이 결제함+포그 확인 기다리는중
    PAYMENT_ERROR("결제오류"),
    CONFIRMED("예약확정"), // 포그측에서 결제 완료 확인함
    FILMING("촬영진행"),
    COMPLETED("촬영완료");

    private final String name; //한국어 이름

    // 영어 enum값을 한글로 반환
    public String getName() {
        return name;
    }
}

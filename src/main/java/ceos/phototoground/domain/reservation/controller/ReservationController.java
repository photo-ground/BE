package ceos.phototoground.domain.reservation.controller;

import ceos.phototoground.domain.reservation.dto.PaymentRequestDTO;
import ceos.phototoground.domain.reservation.dto.PhotographerReservationInfo;
import ceos.phototoground.domain.reservation.dto.RequestReservationDTO;
import ceos.phototoground.domain.reservation.dto.ReservationInfoResponse;
import ceos.phototoground.domain.reservation.dto.ReservationStateDTO;
import ceos.phototoground.domain.reservation.dto.ReservationStatusInfo;
import ceos.phototoground.domain.reservation.service.ReservationService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    // 예약신청 페이지 조회
    @GetMapping("/photographer/{photographerId}/reservation")
    public ResponseEntity<PhotographerReservationInfo> getPhotographerReservationInfo(
            @PathVariable("photographerId") Long photographerId) {

        PhotographerReservationInfo info = reservationService.getPhotographerReservationInfo(photographerId);
        return ResponseEntity.ok(info);
    }

    // 예약신청  (로그인 완성된 후 @AuthenticationPrincipal로 수정하기)
    @PostMapping("/reservation/{photographerId}/{customerId}")
    public ResponseEntity<Map<String, String>> createReservation(@PathVariable("photographerId") Long photographerId,
                                                                 @RequestBody @Valid
                                                                 RequestReservationDTO requestReservationDTO,
                                                                 @PathVariable("customerId") Long customerId) {

        reservationService.createReservation(requestReservationDTO, photographerId, customerId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "예약 신청이 완료되었습니다.");
        return ResponseEntity.ok(response);

    }

    // 예약 취소
    @PatchMapping("/reservation/{reservationId}/cancel")
    public ResponseEntity<Map<String, String>> cancelReservation(@PathVariable("reservationId") Long reservationId) {

        reservationService.cancelReservation(reservationId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "예약 취소가 완료되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 예약 상세조회
    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<ReservationInfoResponse> getOneReservationDetail(
            @PathVariable("reservationId") Long reservationId) {

        ReservationInfoResponse dto = reservationService.getOneReservationDetail(reservationId);
        return ResponseEntity.ok(dto);
    }

    // 입금 확인 요청
    @PatchMapping("/reservation/{reservationId}/payment")
    public ResponseEntity<ReservationStateDTO> sendPaymentRequest(@PathVariable("reservationId") Long reservationId,
                                                                  @RequestBody PaymentRequestDTO paymentRequestDTO) {

        ReservationStateDTO dto = reservationService.sendPaymentRequest(reservationId, paymentRequestDTO);
        return ResponseEntity.ok(dto);
    }

    // 예약 현황 조회
    @GetMapping("/reservation/info/{customerId}")
    public ResponseEntity<ReservationStatusInfo> getReservationStatus(@PathVariable Long customerId,
                                                                      @RequestParam String yearMonth) {

        ReservationStatusInfo dto = reservationService.getReservationStatus(customerId, yearMonth);
        return ResponseEntity.ok(dto);
    }

}

package ceos.phototoground.reservation.controller;

import ceos.phototoground.reservation.dto.PhotographerReservationInfo;
import ceos.phototoground.reservation.dto.RequestReservationDTO;
import ceos.phototoground.reservation.service.ReservationService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    // 예약신청 페이지 조회
    @GetMapping("/{photographerId}")
    public ResponseEntity<PhotographerReservationInfo> getPhotographerReservationInfo(
            @PathVariable("photographerId") Long photographerId) {

        PhotographerReservationInfo info = reservationService.getPhotographerReservationInfo(photographerId);
        return ResponseEntity.ok(info);
    }

    // 예약신청  (로그인 완성된 후 @AuthenticationPrincipal로 수정하기)
    @PostMapping("/{photographerId}/{customerId}")
    public ResponseEntity<Map<String, String>> createReservation(@PathVariable("photographerId") Long photographerId,
                                                                 @RequestBody
                                                                 RequestReservationDTO requestReservationDTO,
                                                                 @PathVariable("customerId") Long customerId) {

        reservationService.createReservation(requestReservationDTO, photographerId, customerId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "예약 신청이 완료되었습니다.");
        return ResponseEntity.ok(response);

    }
}

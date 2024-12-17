package ceos.phototoground.reservation.controller;

import ceos.phototoground.reservation.dto.PhotographerReservationInfo;
import ceos.phototoground.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}

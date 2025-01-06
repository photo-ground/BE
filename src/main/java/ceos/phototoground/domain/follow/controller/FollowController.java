package ceos.phototoground.domain.follow.controller;

import ceos.phototoground.domain.customer.dto.CustomUserDetails;
import ceos.phototoground.domain.customer.entity.Customer;
import ceos.phototoground.domain.follow.dto.FollowResponseDto;
import ceos.phototoground.domain.follow.service.FollowService;
import ceos.phototoground.domain.photographer.entity.Photographer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 작가 팔로우하기
    @PostMapping("/{photographerId}")
    public ResponseEntity<Map<String, String>> followPhotographer(
            @PathVariable Long photographerId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 로그인된 사용자 정보 가져오기
        Customer customer = userDetails.getCustomer();

        // 팔로우 서비스 호출
        followService.followPhotographer(customer, photographerId);

        // 성공 응답 메시지 작성
        Map<String, String> response = Map.of(
                "status", "success",
                "message", "작가 팔로우에 성공하였습니다."
        );

        return ResponseEntity.ok(response);
    }

    // 작가 팔로우 취소
    @DeleteMapping("/{photographerId}")
    public ResponseEntity<Map<String, String>> unfollowPhotographer(
            @PathVariable Long photographerId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 로그인된 사용자 정보 가져오기
        Customer customer = userDetails.getCustomer();

        // 팔로우 취소 서비스 호출
        followService.unfollowPhotographer(customer, photographerId);

        // 성공 응답 메시지 작성
        Map<String, String> response = Map.of(
                "status", "success",
                "message", "작가 팔로우 취소에 성공하였습니다."
        );

        return ResponseEntity.ok(response);
    }

    // 팔로우 하는 작가 전체 조회
    @GetMapping
    public ResponseEntity<List<FollowResponseDto>> getAllFollowedPhotographers(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 로그인된 사용자 정보 가져오기
        Customer customer = userDetails.getCustomer();

        // 팔로우한 작가 리스트 조회 및 DTO 변환
        List<FollowResponseDto> photographers = followService.getAllFollowedPhotographers(customer);

        return ResponseEntity.ok(photographers);
    }


}
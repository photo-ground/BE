package ceos.phototoground.customer.controller;

import ceos.phototoground.customer.dto.CustomerJoinRequestDto;
import ceos.phototoground.customer.service.CustomerService;
import ceos.phototoground.global.dto.SuccessResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/join")
    public ResponseEntity joinProcess(@Valid @RequestBody CustomerJoinRequestDto customerJoinRequestDto) {
        customerService.joinCustomer(customerJoinRequestDto);

        return ResponseEntity.ok(
                SuccessResponseDto.successMessage("회원가입이 완료되었습니다.")
        );
    }
}

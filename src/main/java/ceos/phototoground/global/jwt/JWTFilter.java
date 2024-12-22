package ceos.phototoground.global.jwt;

import ceos.phototoground.customer.domain.UserRole;
import ceos.phototoground.customer.dto.CustomUserDetails;
import ceos.phototoground.customer.domain.Customer;
import ceos.phototoground.global.dto.ErrorResponseDto;
import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import ceos.phototoground.global.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("access");

        // 토큰이 없다면 다음 필터로 넘김 (권한이 필요없는 경우)
        if (accessToken == null) {

            filterChain.doFilter(request, response);

            return;
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음 (응답 코드 발생하여 에러 띄워야함)
        // 프론트와 합의 후 상태 코드 전달
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰에서 username과 role 획득
        String email = jwtUtil.getUsername(accessToken);
        UserRole role = UserRole.fromAuthority(jwtUtil.getRole(accessToken));

        // 회원 생성
        Customer customer = Customer.builder()
                                    .email(email)
                                    .password("tempPassword") // 비밀번호는 실제 사용되지 않으므로 임의 값
                                    .role(role)
                                    .build();

        // UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(customer);

        // 스프링 시큐리티 인증 토큰 생성 및 세션에 사용자 등록
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorResponseDto errorResponse) throws IOException {
        response.setStatus(errorResponse.getStatus()); // HTTP 상태 코드 설정
        response.setContentType("application/json; charset=UTF-8"); // UTF-8 설정
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse)); // JSON 응답 작성
    }
}

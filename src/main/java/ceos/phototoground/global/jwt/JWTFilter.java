package ceos.phototoground.global.jwt;

import ceos.phototoground.customer.domain.UserRole;
import ceos.phototoground.customer.dto.CustomUserDetails;
import ceos.phototoground.customer.domain.Customer;
import ceos.phototoground.global.dto.ErrorResponseDto;
import ceos.phototoground.global.exception.CustomException;
import ceos.phototoground.global.exception.ErrorCode;
import ceos.phototoground.global.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

        String path = request.getRequestURI();

        // 회원가입 경로 등 인증이 필요 없는 경로에 대해 필터를 건너뜀
        if ("/api/customer/join".equals(path) || "/login".equals(path) || "/env".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Authorization 헤더 확인
            String authorization = request.getHeader("Authorization");

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                sendErrorResponse(response, ErrorResponseDto.error(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "로그인 실패",
                        "Authorization 헤더가 없거나 Bearer 토큰이 아닙니다."
                ));
                return;
            }

            // Bearer 부분 제거 후 순수 토큰 획득
            String token = authorization.split(" ")[1];

            // 토큰 소멸 시간 검증
            if (jwtUtil.isExpired(token)) {
                sendErrorResponse(response, ErrorResponseDto.error(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "로그인 실패",
                        "JWT 토큰이 만료되었습니다."
                ));
                return;
            }

            // 토큰에서 username과 role 획득
            String email = jwtUtil.getUsername(token);
            UserRole role = UserRole.fromAuthority(jwtUtil.getRole(token));

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

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            sendErrorResponse(response, ErrorResponseDto.error(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "로그인 실패",
                    "JWT 토큰이 만료되었습니다."
            ));
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            sendErrorResponse(response, ErrorResponseDto.error(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "로그인 실패",
                    "JWT 토큰 형식이 올바르지 않습니다."
            ));
        } catch (io.jsonwebtoken.SignatureException e) {
            sendErrorResponse(response, ErrorResponseDto.error(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "로그인 실패",
                    "JWT 토큰의 서명이 유효하지 않습니다."
            ));
        } catch (io.jsonwebtoken.JwtException e) {
            sendErrorResponse(response, ErrorResponseDto.error(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "로그인 실패",
                    "JWT 토큰이 유효하지 않습니다."
            ));
        }
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorResponseDto errorResponse) throws IOException {
        response.setStatus(errorResponse.getStatus()); // HTTP 상태 코드 설정
        response.setContentType("application/json; charset=UTF-8"); // UTF-8 설정
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse)); // JSON 응답 작성
    }
}
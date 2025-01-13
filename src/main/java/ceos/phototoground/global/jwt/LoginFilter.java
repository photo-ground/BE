package ceos.phototoground.global.jwt;

import ceos.phototoground.domain.customer.dto.LoginRequestDto;
import ceos.phototoground.global.dto.ErrorResponseDto;
import ceos.phototoground.global.dto.SuccessResponseDto;
import ceos.phototoground.global.entity.RefreshEntity;
import ceos.phototoground.global.entity.RefreshRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            // 요청 본문에서 JSON 데이터를 읽어 DTO로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequestDto loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            // UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(email, password, null);

            // AuthenticationManager로 인증 진행
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse login request", e);
        }
    }

    // authenticationManager에서 검증 후, 로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException {
        // 인증된 사용자 정보 가져오기
        String username = authentication.getName(); //username 대신 email 사용중
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority(); // 첫 번째 권한 추출

        // JWT 토큰 생성 (Access Token: 10분, Refresh Token: 24시간)
        String accessToken = jwtUtil.createJwt("access", username, role, 3600000L); // 1시간
        String refreshToken = jwtUtil.createJwt("refresh", username, role, 86400000L); // 24시간

        //Refresh 토큰 저장
        addRefreshEntity(username, refreshToken, 86400000L);

        // Access Token을 헤더에 추가
        response.setHeader("Authorization", "Bearer " + accessToken);

        // Refresh Token을 HttpOnly Cookie로 설정
        Cookie refreshCookie = createCookie("refresh", refreshToken);
        response.addCookie(refreshCookie);

        // 응답 바디에 성공 메시지 추가
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        SuccessResponseDto<String> successResponse = SuccessResponseDto.successMessage("로그인 성공");
        response.getWriter().write(new ObjectMapper().writeValueAsString(successResponse));
        response.getWriter().flush();
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        // 실패 원인에 따라 메시지 설정
        String errorMessage;
        if (failed instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 잘못되었습니다.";
        } else {
            errorMessage = "로그인에 실패했습니다. 다시 시도해주세요.";
        }

        // 실패 응답 DTO 생성
        ErrorResponseDto errorResponse = ErrorResponseDto.error(
                HttpServletResponse.SC_UNAUTHORIZED, // 401 Unauthorized
                "로그인 실패",
                errorMessage
        );

        // 응답 타입 및 상태 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 상태 설정

        // JSON 응답 작성
        response.getWriter()
                .write(new ObjectMapper().writeValueAsString(errorResponse));
        response.getWriter()
                .flush();
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);

        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        //cookie.setPath("/");

        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        // 클라이언트 요청에서 "username" 대신 "email" 필드 값 추출
        return request.getParameter("email");
    }
}

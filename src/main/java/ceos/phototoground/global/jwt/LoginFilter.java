package ceos.phototoground.global.jwt;

import ceos.phototoground.Auth.dto.CustomUserDetails;
import ceos.phototoground.global.dto.ErrorResponseDto;
import ceos.phototoground.global.dto.SuccessResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        //클라이언트 요청에서 email, password 추출
        String email = obtainUsername(request);
        String password = obtainPassword(request);

        //스프링 시큐리티에서 email과 password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달 (검증 진행)
        return authenticationManager.authenticate(authToken);
    }

    // authenticationManager에서 검증 후, 로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException {
        // Spring Security의 인증 객체에서 사용자 정보를 가져옴
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = customUserDetails.getUsername();

        // 인증된 사용자의 권한 목록을 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // 권한 컬렉션에서 첫 번째 권한을 가져옴 (여기서는 단일 권한만 사용한다고 가정)
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // JWT 토큰 생성. 이메일과 역할(role)을 포함하며, 토큰 만료 시간은 10시간으로 설정
        String token = jwtUtil.createJwt(email, role, 60 * 60 * 10000L);

        // JWT를 Authorization 헤더에 추가
        response.addHeader("Authorization", "Bearer " + token);

        // 응답 바디에 성공 메시지 추가
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 공통 성공 응답 생성
        SuccessResponseDto<String> successResponse = SuccessResponseDto.successMessage("로그인 성공");

        // 응답 바디에 JSON으로 성공 응답 반환
        response.getWriter()
                .write(new ObjectMapper().writeValueAsString(successResponse));
        response.getWriter()
                .flush();
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

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        // 클라이언트 요청에서 "username" 대신 "email" 필드 값 추출
        return request.getParameter("email");
    }
}

package ceos.phototoground.global.jwt;

import ceos.phototoground.domain.customer.entity.UserRole;
import ceos.phototoground.domain.customer.dto.CustomUserDetails;
import ceos.phototoground.domain.customer.entity.Customer;
import ceos.phototoground.domain.customer.repository.CustomerRepository;
import ceos.phototoground.global.dto.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CustomerRepository customerRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 헤더에서 Authorization 키를 가져옴
        String accessToken = request.getHeader("Authorization");

        // Authorization 헤더가 있고 Bearer 접두사로 시작하면 토큰만 추출
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7); // "Bearer " 이후의 토큰만 가져오기
        } else {
            // Authorization 헤더가 없거나 올바르지 않으면 다음 필터로 진행
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // JWT 토큰의 만료 여부 확인
            jwtUtil.isExpired(accessToken);

            // 토큰의 category 확인 (access token이어야 함)
            String category = jwtUtil.getCategory(accessToken);
            if (!category.equals("access")) {
                sendErrorResponse(response, "Invalid token category", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // 토큰에서 email(사용자 식별자) 추출
            String email = jwtUtil.getUsername(accessToken);

            // 데이터베이스에서 사용자 정보 조회
            Customer customer = customerRepository.findByEmail(email)
                                                  .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            // 사용자 정보로 인증 객체 생성
            CustomUserDetails customUserDetails = new CustomUserDetails(customer);
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    customUserDetails, null, customUserDetails.getAuthorities());

            // SecurityContext에 인증 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (ExpiredJwtException e) {
            // 토큰 만료 예외 처리
            sendErrorResponse(response, "Access token expired", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (UsernameNotFoundException e) {
            // 사용자 정보를 찾지 못했을 때 처리
            sendErrorResponse(response, e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
            // 기타 예외 처리
            sendErrorResponse(response, "Invalid access token", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(
                Map.of(
                        "status", status,
                        "error", HttpStatus.valueOf(status).getReasonPhrase(),
                        "message", message
                )
        ));
    }

}

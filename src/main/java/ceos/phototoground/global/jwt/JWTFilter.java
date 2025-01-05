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

        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("access");

        // 토큰이 없다면 다음 필터로 넘김 (권한이 필요 없는 경우)
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 토큰의 유효성 검사
            jwtUtil.isExpired(accessToken);

            // 토큰의 category 확인
            String category = jwtUtil.getCategory(accessToken);
            if (!category.equals("access")) {
                sendErrorResponse(response, "Invalid token category", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // email 추출 및 사용자 로드
            String email = jwtUtil.getUsername(accessToken);
            Customer customer = customerRepository.findByEmail(email)
                                                  .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            // 인증 객체 생성
            CustomUserDetails customUserDetails = new CustomUserDetails(customer);
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, "Access token expired", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (UsernameNotFoundException e) {
            sendErrorResponse(response, e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
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

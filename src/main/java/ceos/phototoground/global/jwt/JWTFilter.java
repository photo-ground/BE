package ceos.phototoground.global.jwt;

import ceos.phototoground.domain.customer.dto.CustomUserDetails;
import ceos.phototoground.domain.customer.entity.Customer;
import ceos.phototoground.domain.customer.repository.CustomerRepository;
import ceos.phototoground.domain.photographer.entity.Photographer;
import ceos.phototoground.domain.photographer.repository.PhotographerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.AllArgsConstructor;
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
    private final PhotographerRepository photographerRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");
        System.out.println("🔍 [JWTFilter] 요청에서 Authorization 헤더를 가져왔습니다: " + accessToken);

        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        } else {
            System.out.println("⚠️ [JWTFilter] 유효한 Authorization 헤더를 찾을 수 없습니다. 인증 없이 필터 체인을 진행합니다.");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwtUtil.isExpired(accessToken); // 토큰 만료 확인
            String category = jwtUtil.getCategory(accessToken);
            if (!"access".equals(category)) {
                sendErrorResponse(response, "유효하지 않은 토큰 카테고리입니다.", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String email = jwtUtil.getUsername(accessToken); // 이메일 파싱
            String role = jwtUtil.getRole(accessToken); // 역할 파싱

            // 사용자 조회 및 인증 객체 생성
            Authentication authToken;
            if ("ROLE_CUSTOMER".equals(role)) {
                Customer customer = customerRepository.findByEmail(email)
                                                      .orElseThrow(() -> new UsernameNotFoundException("Customer not found with email: " + email));
                CustomUserDetails customUserDetails = new CustomUserDetails(customer);
                authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            } else if ("ROLE_PHOTOGRAPHER".equals(role)) {
                Photographer photographer = photographerRepository.findByEmail(email)
                                                                  .orElseThrow(() -> new UsernameNotFoundException("Photographer not found with email: " + email));
                CustomUserDetails customUserDetails = new CustomUserDetails(photographer);
                authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            } else {
                throw new IllegalArgumentException("유효하지 않은 역할 정보입니다: " + role);
            }

            // SecurityContext에 인증 정보 설정
            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println("🔒 [JWTFilter] SecurityContext에 인증 정보를 설정했습니다. 사용자: " + email);

        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, "토큰이 만료되었습니다.", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (UsernameNotFoundException | IllegalArgumentException e) {
            sendErrorResponse(response, e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, "유효하지 않은 액세스 토큰입니다.", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

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

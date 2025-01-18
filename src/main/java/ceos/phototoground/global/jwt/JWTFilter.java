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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CustomerRepository customerRepository;

    private static final Logger log = LoggerFactory.getLogger(JWTFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 헤더에서 Authorization 키를 가져옴
        String accessToken = request.getHeader("Authorization");
        System.out.println("🔍 [JWTFilter] 요청에서 Authorization 헤더를 가져왔습니다: " + accessToken);

        // Authorization 헤더가 있고 Bearer 접두사로 시작하면 토큰만 추출
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7); // "Bearer " 이후의 토큰만 가져오기
            System.out.println("🔑 [JWTFilter] Bearer 토큰 추출 완료: " + accessToken);
        } else {
            System.out.println("⚠️ [JWTFilter] 유효한 Authorization 헤더를 찾을 수 없습니다. 인증 없이 필터 체인을 진행합니다.");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // JWT 토큰의 만료 여부 확인
            System.out.println("🕒 [JWTFilter] 토큰의 만료 여부를 확인합니다.");
            jwtUtil.isExpired(accessToken);

            // 토큰의 category 확인 (access token이어야 함)
            String category = jwtUtil.getCategory(accessToken);
            System.out.println("📂 [JWTFilter] 토큰 카테고리 확인: " + category);
            if (!"access".equals(category)) {
                System.out.println("❌ [JWTFilter] 유효하지 않은 토큰 카테고리: " + category);
                sendErrorResponse(response, "유효하지 않은 토큰 카테고리입니다.", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // 토큰에서 email(사용자 식별자) 추출
            String email = jwtUtil.getUsername(accessToken);
            System.out.println("📧 [JWTFilter] 토큰에서 이메일을 추출했습니다: " + email);

            // 데이터베이스에서 사용자 정보 조회
            Customer customer = customerRepository.findByEmail(email)
                                                  .orElseThrow(() -> new UsernameNotFoundException("이메일로 사용자를 찾을 수 없습니다: " + email));
            System.out.println("✅ [JWTFilter] 데이터베이스에서 사용자 정보를 가져왔습니다: " + customer);

            // 사용자 정보로 인증 객체 생성
            CustomUserDetails customUserDetails = new CustomUserDetails(customer);
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    customUserDetails, null, customUserDetails.getAuthorities());

            // SecurityContext에 인증 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println("🔒 [JWTFilter] SecurityContext에 인증 정보를 설정했습니다. 사용자: " + email);

        } catch (ExpiredJwtException e) {
            // 토큰 만료 예외 처리
            System.out.println("⏰ [JWTFilter] 토큰이 만료되었습니다: " + e.getMessage());
            sendErrorResponse(response, "토큰이 만료되었습니다.", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (UsernameNotFoundException e) {
            // 사용자 정보를 찾지 못했을 때 처리
            System.out.println("❌ [JWTFilter] 사용자를 찾을 수 없습니다: " + e.getMessage());
            sendErrorResponse(response, e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
            // 기타 예외 처리
            System.out.println("⚠️ [JWTFilter] 액세스 토큰이 유효하지 않습니다: " + e.getMessage());
            e.printStackTrace(); // 예외 스택 트레이스 출력
            sendErrorResponse(response, "유효하지 않은 액세스 토큰입니다.", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 필터 체인 계속 진행
        System.out.println("✔️ [JWTFilter] 요청 처리가 완료되었습니다. 필터 체인을 계속 진행합니다.");
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

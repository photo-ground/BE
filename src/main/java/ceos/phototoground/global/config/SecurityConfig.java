package ceos.phototoground.global.config;

import ceos.phototoground.domain.customer.repository.CustomerRepository;
import ceos.phototoground.domain.photographer.repository.PhotographerRepository;
import ceos.phototoground.global.entity.RefreshRepository;
import ceos.phototoground.global.jwt.CustomLogoutFilter;
import ceos.phototoground.global.jwt.JWTFilter;
import ceos.phototoground.global.jwt.JWTUtil;
import ceos.phototoground.global.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity //Security config 설정
@RequiredArgsConstructor
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final CustomerRepository customerRepository;
    private final PhotographerRepository photographerRepository;

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { // 비밀번호 암호화
        // 비밀번호를 안전하게 저장하기 위해 사용하는 BCrypt 해싱 알고리즘.
        // 회원가입 시 비밀번호를 암호화하고, 인증 시 암호화된 비밀번호와 비교하는 데 사용됨.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CORS 활성화
        http.cors(c -> c.configurationSource(new CorsConfig().corsConfigurationSource()));

        //csrf disable
        http.csrf((auth) -> auth.disable());

        //From 로그인 방식 disable
        http.formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        http.httpBasic((auth) -> auth.disable());

        //경로별 인가 작업
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login", "/", "/api/customer/join", "/api/customer/emails/request",
                        "/api/customer/emails/verify", "/api/photographer/{photographerId}/review",
                        "api/review/{reviewId}", "/api/spot/{spotId}", "/api/photographer", "/api/photographer/search",
                        "/api/photographer/{photographerId}/intro", "/api/photographer/{photographerId}/bottom",
                        "/api/photographer/active",
                        "/api/posts/{postId}", "/api/posts", "/error", "/api/spot")
                .permitAll() // 해당 경로는 모든 사용자가 접근 가능
                .requestMatchers("/customer_test").hasAuthority("ROLE_CUSTOMER") // 고객만 접근 가능
                .requestMatchers("/photographer_test").hasAuthority("ROLE_PHOTOGRAPHER") // 작가만 접근 가능
                .requestMatchers("/api/reissue").permitAll() // 리프레시 토큰은 모든 사용자가 접근 가능
                .anyRequest().authenticated()); // 이외의 남은 경로는 로그인한 사용자만 접근 가능

        // JWT 권한 검증
        http.addFilterAfter(new JWTFilter(jwtUtil, customerRepository, photographerRepository), LoginFilter.class);

        // 로그인 필터 설정 (/login)
        http.addFilterAt(
                new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository, photographerRepository),
                UsernamePasswordAuthenticationFilter.class);

        // 로그아웃 필터 설정 (/logout)
        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);

        //세션 설정 (JWT는 세션이 stateless 상태로 설정되어야 함)
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
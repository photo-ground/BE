package ceos.phototoground.global.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        //와일카드는 Pattern 사용해야 함
        config.setAllowedOriginPatterns(List.of("https://*.photoground.pages.dev"));
        config.setAllowedOrigins(
                List.of("http://localhost:3001", "http://localhost:3000", "https://localhost:3000",
                        "https://photoground.pages.dev",
                        "https://photoground.pages.dev/",
                        "https://www.photo-ground.dev",
                        "https://www.photo-ground.com"
                ));
        config.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")); //OPTIONS : Preflight 요청에 사용
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Set-Cookie"));
        config.setAllowCredentials(true);

        // 모든 URL(/**)에 대해 설정을 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;

    }
}

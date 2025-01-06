package ceos.phototoground.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        // 컨트롤러 경로에 대해 요청을 허용
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:3001")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")  //OPTIONS : Preflight 요청에 사용
                .exposedHeaders("Authorization", "Set-Cookie")
                .allowedHeaders("*")
                .maxAge(3600L)
                .allowCredentials(true);
    }
}
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
                .allowedOrigins("http://localhost:3000", "http://localhost:3031")
                .allowedOriginPatterns("*")
                .exposedHeaders("Set-Cookie")
                .allowCredentials(true);
    }
}
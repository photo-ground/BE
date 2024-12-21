package ceos.phototoground.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryTemplateConfig {

    @Bean
    public RetryTemplate retryTemplate() {

        return RetryTemplate.builder()
                .maxAttempts(3)   // 최대 3회 재시도
                .fixedBackoff(2000L)  // 재시도 간격 2초
                .retryOn(Exception.class)
                .build();
    }
}

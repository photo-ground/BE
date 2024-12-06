package ceos.phototoground.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig {
    @Bean(name="ImageUploadExecutor")
    public Executor imageUploadExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(9);
        executor.setMaxPoolSize(18);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("ImageUpload-");
        executor.initialize();
        return executor;
    }
}

package ceos.phototoground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling //스케줄링 활성화
public class PhtotogroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhtotogroundApplication.class, args);
    }

}

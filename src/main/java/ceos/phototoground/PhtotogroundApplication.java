package ceos.phototoground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PhtotogroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhtotogroundApplication.class, args);
	}

}

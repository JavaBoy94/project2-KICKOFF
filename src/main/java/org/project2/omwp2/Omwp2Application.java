package org.project2.omwp2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Omwp2Application {

	public static void main(String[] args) {
		SpringApplication.run(Omwp2Application.class, args);
	}

}

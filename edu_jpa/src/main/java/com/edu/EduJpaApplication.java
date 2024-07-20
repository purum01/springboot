package com.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EduJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduJpaApplication.class, args);
	}

}

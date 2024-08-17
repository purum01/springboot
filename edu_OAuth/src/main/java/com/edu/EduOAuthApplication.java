package com.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EduOAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduOAuthApplication.class, args);
	}

}

package com.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EduServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduServiceApplication.class, args);
	}

}

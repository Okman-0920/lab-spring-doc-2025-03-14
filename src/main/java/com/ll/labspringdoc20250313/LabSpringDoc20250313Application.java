package com.ll.labspringdoc20250313;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LabSpringDoc20250313Application {

	public static void main(String[] args) {
		SpringApplication.run(LabSpringDoc20250313Application.class, args);
	}

}

package com.thee5176.webarchive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaRepositories("com.thee5176.webarchive.Repository")
@EntityScan("com.thee5176.webarchive.model")
@ComponentScan
@SpringBootApplication
public class SpringBootWebArchiveApplication {

	@GetMapping("health-check")
	public String healthCheck() {
		return "OK";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebArchiveApplication.class, args);
	}
}

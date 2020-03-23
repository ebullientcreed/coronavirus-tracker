package com.project.coronavirusbackend.coronavirusbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
//EnableScheduling ensures that a background task executor is created
@SpringBootApplication
@EnableScheduling
public class CoronavirusBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronavirusBackendApplication.class, args);
	}

}

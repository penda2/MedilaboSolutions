package com.medilabo_solutions.patient_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class DotenvConfig {

	@Bean
	public Dotenv dotenv() {
		return Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();
	}
}

package com.medilabo_solutions.patient_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// Ajouté pour simplifier la configuration des appels vers les microservices externes
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	// Ajouté pour chiffrer les mots de passe avec l'algorithme BCrypt
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Création d'utilisateurs en mémoire pour tester l'application
	@Bean
	public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
		UserDetails user1 = User.withUsername("user1").password(passwordEncoder.encode("password1"))
				.authorities(new ArrayList<>()).build();

		UserDetails user2 = User.withUsername("user2").password(passwordEncoder.encode("password2"))
				.authorities(new ArrayList<>()).build();

		return new InMemoryUserDetailsManager(user1, user2);
	}

	// Sécurisation des requêtes d'accessibilité des pages
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.httpBasic(withDefaults());

		return http.build();
	}
}

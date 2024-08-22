package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig1 {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http    
		        .authorizeHttpRequests(	authorize -> authorize
				.requestMatchers("/","/member/new","/member/login").permitAll()
				.anyRequest().authenticated())
                .csrf(csrf -> csrf.disable());
		
				
		return http.build();
	}


}

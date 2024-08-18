package com.edu.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.edu.jwt.JWTUtil;
import com.edu.security.ApiCheckFilter;
import com.edu.security.ApiLoginFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
    private final AuthenticationConfiguration configuration;
    private final JWTUtil jwtUtil;
    
    public WebSecurityConfig(AuthenticationConfiguration configuration, JWTUtil jwtUtil) {
        this.configuration = configuration;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter("/notes/**/*", jwtUtil);
    }

    @Bean
    public ApiLoginFilter apiLoginFilter() throws Exception {
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login", jwtUtil);
        apiLoginFilter.setAuthenticationManager(configuration.getAuthenticationManager());        
        return apiLoginFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	.csrf(csrf -> csrf.disable())
        	.formLogin(auth->auth.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/notes/**","/api/login").permitAll() 
                .anyRequest().authenticated()      
            )
            
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        http
            .addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(apiLoginFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


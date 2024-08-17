package com.edu.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/").permitAll() // 홈 페이지는 모두에게 허용
                    .anyRequest().authenticated() // 그 외의 요청은 인증 필요
            )
            
            .oauth2Login(oauth2Login -> // OAuth2 로그인 사용
                oauth2Login
                    .loginPage("/login") // OAuth2 로그인을 포함한 모든 인증은 동일한 로그인 페이지 사용
                    
            );

        return http.build();
    }
}

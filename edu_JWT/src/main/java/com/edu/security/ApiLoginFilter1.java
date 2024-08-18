package com.edu.security;


import java.io.IOException;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApiLoginFilter1 extends AbstractAuthenticationProcessingFilter {

    public ApiLoginFilter1(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        // POST 메서드만 허용
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String email = request.getParameter("email");
        String pw = "1111";

        if (email == null || email.isEmpty()) {
            throw new AuthenticationServiceException("Email cannot be null or empty");
        }

        // UsernamePasswordAuthenticationToken을 생성하여 인증을 처리
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, pw);

        // 인증 관리자로 전달
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}

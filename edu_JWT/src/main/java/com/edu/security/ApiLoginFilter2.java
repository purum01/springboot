package com.edu.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApiLoginFilter2 extends AbstractAuthenticationProcessingFilter {

	public ApiLoginFilter2(String defaultFilterProcessesUrl) {
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		
		System.out.println(email+"/"+pwd);

		// UsernamePasswordAuthenticationToken을 생성하여 인증을 처리
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, pwd);

		// 인증 관리자로 전달
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("인증에 성공하셨습니다 : " + authResult);
		System.out.println(authResult.getPrincipal());

	}
}

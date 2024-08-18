package com.edu.security;

import java.io.IOException;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApiCheckFilter1 extends OncePerRequestFilter {

	private AntPathMatcher antPathMatcher;
	private String pattern;

	public ApiCheckFilter1(String pattern) {
		this.antPathMatcher = new AntPathMatcher();
		this.pattern = pattern;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (antPathMatcher.match(pattern, request.getRequestURI())) {
			System.out.println("================ ApiCheckFilter =====================");
			return;
		}
		
		filterChain.doFilter(request, response);

	}

}

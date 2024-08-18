package com.edu.security;

import java.io.IOException;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApiCheckFilter2 extends OncePerRequestFilter {

	private AntPathMatcher antPathMatcher;
	private String pattern;

	public ApiCheckFilter2(String pattern) {
		this.antPathMatcher = new AntPathMatcher();
		this.pattern = pattern;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (antPathMatcher.match(pattern, request.getRequestURI())) {
			boolean checkHeader = checkAuthHeader(request);
			if (checkHeader) {
				filterChain.doFilter(request, response);
				return;
			}
			return;
		}

		filterChain.doFilter(request, response);

	}

	private boolean checkAuthHeader(HttpServletRequest request) {
		boolean checkResult = false;
		String authHeader = request.getHeader("Authorization");
		if (StringUtils.hasText(authHeader)) {
			if (authHeader.equals("1234567")) {
				checkResult = true;
			}
		}

		return checkResult;
	}

}

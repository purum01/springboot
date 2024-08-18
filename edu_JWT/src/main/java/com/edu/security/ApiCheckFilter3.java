package com.edu.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApiCheckFilter3 extends OncePerRequestFilter {

	private AntPathMatcher antPathMatcher;
	private String pattern;

	public ApiCheckFilter3(String pattern) {
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
			} else {
				try {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					response.setContentType("application/json;charset=utf-8");
					JSONObject json = new JSONObject();
					String message = "FAIL CHECK API TOKEN";
					json.put("code", "403");
					json.put("message", message);
					PrintWriter out = response.getWriter();
					out.print(json);
				} catch (Exception e) {
					e.printStackTrace();
				}

				return;
			}
		}

		filterChain.doFilter(request, response);

	}

	private boolean checkAuthHeader(HttpServletRequest request) {
		boolean checkResult = false;
		String authHeader = request.getHeader("admin");
		if (StringUtils.hasText(authHeader)) {
			if (authHeader.equals("1234567")) {
				checkResult = true;
			}
		}

		return checkResult;
	}

}

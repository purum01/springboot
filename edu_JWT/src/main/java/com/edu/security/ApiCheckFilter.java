package com.edu.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.edu.dto.ClubAuthMemberDTO;
import com.edu.jwt.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApiCheckFilter extends OncePerRequestFilter {

	private final AntPathMatcher antPathMatcher;
	private final String pattern;
	private final JWTUtil jwtUtil;

	public ApiCheckFilter(String pattern, JWTUtil jwtUtil) {
		this.antPathMatcher = new AntPathMatcher();
		this.pattern = pattern;
		this.jwtUtil = jwtUtil;
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
				sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "403", "FAIL CHECK API TOKEN");
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	private boolean checkAuthHeader(HttpServletRequest request) {
		boolean checkResult = false;
		String authorization = request.getHeader("Authorization");
		if (StringUtils.hasText(authorization)) {
			String token = authorization.split(" ")[1];
			if(jwtUtil.isExpired(token)) {
				System.out.println("token expired");
				return false;
			}
			
			String username = jwtUtil.getUsername(token);
			String role  = jwtUtil.getRole(token);
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
						
			ClubAuthMemberDTO authMember = new ClubAuthMemberDTO(username, "password", Collections.singletonList(authority));
			Authentication authToken = new UsernamePasswordAuthenticationToken(authMember, null, authMember.getAuthorities());

		    SecurityContextHolder.getContext().setAuthentication(authToken);
		    checkResult = true;

		}else {
			System.out.println("token null");
		}

		return checkResult;
	}

	//오류 메시지 응답
	private void sendErrorResponse(HttpServletResponse response, int status, String code, String message) throws IOException {
	    response.setStatus(status);
	    response.setContentType("application/json;charset=utf-8");

	    Map<String, String> responseMap = new HashMap<>();
	    responseMap.put("code", code);
	    responseMap.put("message", message);

	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonResponse = objectMapper.writeValueAsString(responseMap);

	    try (PrintWriter out = response.getWriter()) {
	        out.print(jsonResponse);
	    }
	}
	
}

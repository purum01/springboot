package com.edu.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.edu.dto.ClubAuthMemberDTO;
import com.edu.jwt.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final JWTUtil jwtUtil;
	
	public ApiLoginFilter(String defaultFilterProcessesUrl, JWTUtil jwtUtil) {
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
		this.jwtUtil = jwtUtil;
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

		// 토큰 발급
		ClubAuthMemberDTO authMember = (ClubAuthMemberDTO)authResult.getPrincipal();
		String email = authMember.getEmail();  // 이메일 추출
	
	    Collection<? extends GrantedAuthority> authorities = authMember.getAuthorities();     // Role 추출
	    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
	
		String token = jwtUtil.generateToken(email, role, 60*60*10L*1000);

		response.addHeader("Authorization", "Bearer " + token);
	}
	
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);
    }
}

package com.shop.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MockMvc mockMvc;


	public Member createMember(String email, String password) {
		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setEmail(email);
		memberFormDto.setName("홍길동");
		memberFormDto.setAddress("서울 강남구");
		memberFormDto.setPassword(password);
		return memberService.saveMember(memberFormDto);
	}

	@Test
	@DisplayName("로그인 성공 테스트")
	public void loginSuccessTest() throws Exception {
		String email = "test5@email.com";
		String password = "12345678";
		this.createMember(email, password);

		mockMvc.perform(
				formLogin().userParameter("email").loginProcessingUrl("/member/login").user(email).password(password))
				.andExpect(SecurityMockMvcResultMatchers.authenticated());
	}

	@Test
	@DisplayName("로그인 실패 테스트")
	public void loginFailTest() throws Exception {
		String email = "test6@email.com";
		String password = "12345678";
		this.createMember(email, password);
		mockMvc.perform(
				formLogin().userParameter("email").loginProcessingUrl("/member/login").user(email).password("12345"))
				.andExpect(SecurityMockMvcResultMatchers.unauthenticated());
	}
}

package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.dto.MemberFormDto;
import com.shop.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/new")
	public String memberForm(MemberFormDto memberFormDto) {
		System.out.println("=========> new Get");
		return "/member/memberForm";
	}

	@PostMapping("/new")
	public String memberSave(@Valid MemberFormDto memberFormDto, BindingResult br, Model model) {
		System.out.println("=========>"+memberFormDto);
		if (br.hasErrors()) {
			return "/member/memberForm";
		}
		try {
			memberService.saveMember(memberFormDto);
		} catch (IllegalArgumentException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/member/memberForm";
		}
		return "redirect:/";
	}

	@GetMapping("/login")
	public String loginMember() {
		return "/member/memberLoginForm";
	}

	@GetMapping("/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "/member/memberLoginForm";
	}

}

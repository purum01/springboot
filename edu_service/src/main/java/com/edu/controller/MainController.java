package com.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping("/hello")
	public void test() {
		System.out.println("MainController의 test()가 실행되었습니다.");
	}

}

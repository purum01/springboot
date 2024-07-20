package com.edu.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.beans.Member;
import com.edu.biz.MemberService;

public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String id = request.getParameter("id");
		String pwd = request.getParameter("passwd");
		String name = request.getParameter("name");
		String mail = request.getParameter("mail");

		Member member = new Member();
		member.setId(id);
		member.setPasswd(pwd);
		member.setName(name);
		member.setMail(mail);

		MemberService service = new MemberService();
		service.memberInsert(member);
		
		RequestDispatcher rd = request.getRequestDispatcher("/output.html");
		rd.forward(request, response);
	}
}

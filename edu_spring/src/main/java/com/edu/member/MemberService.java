package com.edu.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	@Autowired
	MemberDAO dao;

	public void memberinsert(Member member) {
		dao.memberInsert(member);
	}

	
}

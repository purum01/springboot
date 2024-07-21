package com.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}

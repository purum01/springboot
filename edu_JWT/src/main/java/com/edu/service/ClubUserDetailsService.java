package com.edu.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edu.dto.ClubAuthMemberDTO;
import com.edu.entity.ClubMember;
import com.edu.repository.ClubMemberRepository;

@Service
public class ClubUserDetailsService implements UserDetailsService {

	private ClubMemberRepository repository;

	public ClubUserDetailsService(ClubMemberRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<ClubMember> result = repository.findByEmail(username, false);

		if (!result.isPresent()) {
			throw new UsernameNotFoundException(username + " 사용자 없음");
		}
		ClubMember clubMember = result.get();

		ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(clubMember.getEmail(), clubMember.getPassword(),
				clubMember.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
						.collect(Collectors.toSet()));
		return clubAuthMember;
	}
}

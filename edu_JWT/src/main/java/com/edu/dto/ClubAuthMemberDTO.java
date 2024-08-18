package com.edu.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ClubAuthMemberDTO extends User {

	private static final long serialVersionUID = 1L;
	private String email;
	private String name;
	private boolean fromSocial;

	public ClubAuthMemberDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.email = username;
	}

}

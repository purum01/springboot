package com.edu.entity;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubMember extends BaseEntity {
	
	@Id
	private String email;
	private String password;
	private String name;
	private boolean fromSocial;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@Builder.Default
	private Set<ClubMemberRole> roleSet = new HashSet<>();
	
	public void addMemberRole(ClubMemberRole clubMemberRole) {
		roleSet.add(clubMemberRole);
	}
}

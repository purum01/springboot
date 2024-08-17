package com.edu.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.edu.entity.ClubMember;
import com.edu.entity.ClubMemberRole;
import com.edu.repository.ClubMemberRepository;

import java.util.Map;
import java.util.HashMap;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class ClubOAuthUserDetailsService extends DefaultOAuth2UserService {
	
	@Autowired
	private ClubMemberRepository repository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)throws OAuth2AuthenticationException {
        // 디버깅 용 로그
        System.out.println("------------------------------------------------------------------------");
        System.out.println("userRequest : " + userRequest);

        // clientName 가져오기
        String clientName= userRequest.getClientRegistration().getClientName();
        System.out.println("ClientName : " + clientName);
        System.out.println(userRequest.getAdditionalParameters());

        // 부모 클래스의 loadUser 메소드 호출
        OAuth2User oAuth2User=super.loadUser(userRequest);

        // 디버깅 용 로그
        System.out.println("========================================================================");
        oAuth2User.getAttributes().forEach((k, v) -> {
            System.out.println(k + ": " + v);
        });

        String email= oAuth2User.getAttribute("email");
        if (email != null) {
            saveSocialMember(email);
        }

        // 필수적인 사용자 정보 추가 로직이나 커스터마이징을 여기에 추가할 수 있음
        return oAuth2User;
    }

    private ClubMember saveSocialMember(String email) {
        Optional<ClubMember> result = repository.findByEmail(email, true);
        if (result.isPresent()) {
            return result.get();
        }

        ClubMember clubMember= ClubMember.builder()
            .email(email)
            .password("{noop}1111")  // PasswordEncoder를 사용하지 않으면 {noop}을 prefix로 사용해 평문을 저장
            .fromSocial(true)
            .build();

        clubMember.addMemberRole(ClubMemberRole.USER);
        repository.save(clubMember);
        return clubMember;
    }
}

package com.shop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
	@Autowired
	private PasswordEncoder passwordEncoder;

    
    private MemberFormDto createMember(String email) {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울 강남구 언주로");
        memberFormDto.setPassword("1234");
        return memberFormDto;
    }

    @Test
    @DisplayName("회원 가입 테스트")
    public void saveMemberTest() {
        // Given: 테스트에 필요한 데이터 생성
        MemberFormDto memberFormDto = createMember("test@email.com");

        // When: 회원을 저장하는 메서드 호출
        Member savedMember = memberService.saveMember(memberFormDto);

        // Then: 저장된 회원의 필드가 예상대로 설정되었는지 확인
        assertMemberDetails(savedMember, memberFormDto);
    }

    private void assertMemberDetails(Member savedMember, MemberFormDto memberFormDto) {
        assertThat(savedMember.getEmail()).isEqualTo(memberFormDto.getEmail());
        assertThat(savedMember.getName()).isEqualTo(memberFormDto.getName());
        assertThat(savedMember.getAddress()).isEqualTo(memberFormDto.getAddress());
        assertThat(passwordEncoder.matches("1234", savedMember.getPassword())).isTrue();
        assertThat(savedMember.getRole()).isEqualTo(Role.USER);
    }


    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest() {
        // Given: 동일한 이메일로 두 명의 회원 생성
        MemberFormDto firstMember = createMember("test2@email.com");
        MemberFormDto duplicateMember = createMember("test2@email.com");

        // When: 첫 번째 회원을 저장
        memberService.saveMember(firstMember);

        // Then: 두 번째 회원을 저장 시도할 때 중복 회원 예외가 발생하는지 확인
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.saveMember(duplicateMember);
        });

        // 예외 메시지가 예상한 대로인지 검증
        assertEquals("이미 가입된 회원입니다.", exception.getMessage());
    }

}

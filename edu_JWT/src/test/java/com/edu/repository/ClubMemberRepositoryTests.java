package com.edu.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.edu.entity.ClubMember;
import com.edu.entity.ClubMemberRole;

@SpringBootTest
public class ClubMemberRepositoryTests {

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testCreateNewMember() {
        // Given
        String rawPassword = "12345";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        ClubMember clubMember = ClubMember.builder()
                .email("guest@kt.com")
                .name("홍길동")
                .password(encodedPassword) // 인코딩된 비밀번호 사용
                .fromSocial(false)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);

        // When
        clubMemberRepository.save(clubMember);

        // Then
        Optional<ClubMember> result = clubMemberRepository.findById("guest@kt.com");
        assertThat(result).isPresent();

        ClubMember savedMember = result.get();
        assertThat(savedMember.getEmail()).isEqualTo("guest@kt.com");
        assertThat(savedMember.getName()).isEqualTo("홍길동");
        assertThat(passwordEncoder.matches(rawPassword, savedMember.getPassword())).isTrue(); // 비밀번호 검증
        assertThat(savedMember.isFromSocial()).isFalse();
        assertThat(savedMember.getRoleSet()).contains(ClubMemberRole.USER);

        // 확인용 출력
        System.out.println(savedMember);
    }
    
    @Test
    @Transactional
    @Rollback(value = false)
    public void testDeleteMemberByEmail() {
        // Given
        String email = "guest@kt.com";

        // When
        clubMemberRepository.deleteById(email);

        // Then
        Optional<ClubMember> result = clubMemberRepository.findById(email);
        assertThat(result).isNotPresent(); // 삭제 후에는 존재하지 않아야 함

        // 확인용 출력
        if (result.isEmpty()) {
            System.out.println("Member with email " + email + " has been successfully deleted.");
        }
    }
}


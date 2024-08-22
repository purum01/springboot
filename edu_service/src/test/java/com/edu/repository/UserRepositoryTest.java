package com.edu.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.edu.entity.User;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testInsertUsers() {
        // 테스트용 사용자 데이터 생성
        List<User> users = Arrays.asList(
                new User("1", "김철수"),
                new User("2", "이영희"),
                new User("3", "박민수"),
                new User("4", "최지우"),
                new User("5", "강호동"),
                new User("6", "유재석"),
                new User("7", "정형돈"),
                new User("8", "김종국"),
                new User("9", "송지효"),
                new User("10", "하하")
        );

        // 사용자 데이터 저장
        userRepository.saveAll(users);

        // 데이터베이스에서 모든 사용자 조회
        List<User> savedUsers = userRepository.findAll();

        // 저장된 사용자의 수가 10명인지 확인
        assertThat(savedUsers.size()).isEqualTo(10);

        // 저장된 사용자의 이름을 확인
        assertThat(savedUsers).extracting(User::getName).containsExactlyInAnyOrder(
                "김철수", "이영희", "박민수", "최지우", "강호동", 
                "유재석", "정형돈", "김종국", "송지효", "하하"
        );
    }
}

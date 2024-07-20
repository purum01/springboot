package com.edu.board.repository;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@MapperScan(basePackages = "com.edu.board.repository") // BoardMybatis 인터페이스가 위치한 패키지를 스캔
public class TestConfig {

	@Bean
	public BoardJDBC boardJDBC() {
		return new BoardJDBC();
	}
}

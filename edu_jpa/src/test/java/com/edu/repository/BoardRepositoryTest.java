package com.edu.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.edu.entity.BoardEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BoardRepositoryTest {
	@Autowired
	private BoardRepository boardRepository;

	@Test
	@DisplayName("게시글 등록이 정상 동작한다.")
	public void insertBoardTest() {
		// given
		BoardEntity board = BoardEntity.builder().title("JPA").writer("이순신").content("자바 표준 ORM 스펙입니다").build();
		// when
		BoardEntity savedBoard = boardRepository.save(board);
		// then
		assertThat(savedBoard.getTitle()).isEqualTo(board.getTitle());
		assertThat(savedBoard.getWriter()).isEqualTo(board.getWriter());
		assertThat(savedBoard.getContent()).isEqualTo(board.getContent());
	}

	@Test
	@DisplayName("게시글 추출이 정상 동작한다.")
	public void getBoardTest() {
		Optional<BoardEntity> board = boardRepository.findById(1L);
		assertTrue(board.isPresent());
	}

	@Test
	@DisplayName("게시글 수정이 정상 동작한다")
	public void updateBoardTest() throws Exception {
		BoardEntity board = boardRepository.findById(1L).orElseThrow(Exception::new);
		board.setTitle("AWS");
		board.setContent("아마존 클라우드 서비스");
		boardRepository.save(board);

		BoardEntity board2 = boardRepository.findById(1L).orElseThrow(Exception::new);
		assertThat(board2.getTitle()).isEqualTo("AWS");
	}

	@Test
	@DisplayName("게시글 전체 조회가 정상 동작한다")
	public void getBoardListTest() {
		List<BoardEntity> boardList = boardRepository.findAll();
		boardList.forEach(System.out::println);
	}

	@Test
	@DisplayName("게시글 삭제가 정상 동작한다")
	public void deleteBoardTest() {
		boardRepository.deleteById(1L);
		assertFalse(boardRepository.findById(1L).isPresent());
	}

}

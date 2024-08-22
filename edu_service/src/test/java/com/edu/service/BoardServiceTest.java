package com.edu.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.edu.dto.BoardDto;
import com.edu.entity.Board;
import com.edu.repository.BoardRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class BoardServiceTest {
	@Autowired
	private BoardService boardService;

	@Autowired
	private BoardRepository boardRepo;

	public BoardDto saveBoardDto() {
		BoardDto BoardDto = new BoardDto();
		BoardDto.setTitle("MVC");
		BoardDto.setWriter("홍길동");
		BoardDto.setContent("Model, View, Controller 디자인 패턴이다");
		return BoardDto;
	}

	@Test
	public void insertBoardTest() {
		BoardDto boardDto = saveBoardDto();
		long seq = boardService.insertBoard(boardDto);

		Board board = boardRepo.findById(seq)
				.orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + seq));

		assertThat(board).isNotNull();
		assertThat(board.getWriter()).isEqualTo(boardDto.getWriter());
		assertThat(board.getTitle()).isEqualTo(boardDto.getTitle());
		assertThat(board.getContent()).isEqualTo(boardDto.getContent());
	}

	@Test
	public void updateBoardTest() {
		BoardDto boardDto = new BoardDto();
		boardDto.setSeq(2L);
		boardDto.setTitle("Spring");
		boardDto.setContent("Backend Java 프레임워크이다");

		long seq = boardService.updateBoard(boardDto);

		Board board = boardRepo.findById(seq)
				.orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + seq));

		assertThat(board).isNotNull();
		assertThat(board.getTitle()).isEqualTo(boardDto.getTitle());
		assertThat(board.getContent()).isEqualTo(boardDto.getContent());
	}

	@Test
	public void getBoardTest() {
		long seq = 1L;
		BoardDto boardDto = boardService.getBoard(seq);

		assertThat(boardDto).isNotNull();
		System.out.println(boardDto);
	}

	@Test
	public void getBoardListTest() {
		BoardDto boardDto = saveBoardDto();
		boardService.insertBoard(boardDto);

		List<BoardDto> boardList = boardService.getBoardList();

		assertThat(boardList).isNotEmpty();
		boardList.forEach(board -> {
			assertThat(board).isNotNull();
			System.out.println(board);
		});
	}

	@Test
	public void deleteBoardTest() {
		long seq = 1L;
		boardService.deleteBoard(seq);

		boolean boardExists = boardRepo.existsById(seq);

		assertThat(boardExists).isFalse();
	}

}

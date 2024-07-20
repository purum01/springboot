package com.edu.board.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.edu.board.vo.BoardVO;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
public class BoardDAOTest {

	@Autowired
	BoardMybatis boardDAO;

	@Test
	@DisplayName("게시글 추출이 정상 동작한다")
	public void getBoardTest() {
		BoardVO vo = BoardVO.builder().seq(1).build();
		BoardVO result = boardDAO.getBoard(vo);
		assertThat(result.getSeq()).isEqualTo(1);
	}

	@Test
	@DisplayName("게시글 등록이 정상 동작한다")
	public void insertBoardTest() {
		// given
		BoardVO vo = BoardVO.builder().title("인사").writer("이순신").content("반갑습니다").build();
		
		// when
		boardDAO.insertBoard(vo);
		BoardVO saveBoard = boardDAO.getBoard(BoardVO.builder().seq(2).build());

		// then
		assertThat(saveBoard).isNotNull();

	}

	@Test
	@DisplayName("게시글 수정이 정상 동작한다")
	public void updateBoardTest() {
		BoardVO vo = BoardVO.builder().seq(2).title("hello").content("have a good day").build();

		boardDAO.updateBoard(vo);
		BoardVO updateBoard = boardDAO.getBoard(vo);

		assertEquals(vo.getTitle(), updateBoard.getTitle());
		assertEquals(vo.getContent(), updateBoard.getContent());
	}

	@Test
	@DisplayName("게시글 삭제가 정상 동작한다")
	public void deleteBoardTest() {
		BoardVO vo = BoardVO.builder().seq(6).build();
		boardDAO.deleteBoard(vo);
		BoardVO deleteBoard = boardDAO.getBoard(vo);
		assertThat(deleteBoard).isNull();
	}

	@Test
	@DisplayName("게시글 전체 조회가 정상 동작한다")
	public void getBoardListTest() {
		assertThat(boardDAO.getBoardList(new BoardVO())).hasSize(1);
	}

}

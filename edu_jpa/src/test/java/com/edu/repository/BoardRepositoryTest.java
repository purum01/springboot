package com.edu.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.edu.entity.BoardEntity;

@SpringBootTest
public class BoardRepositoryTest {
	@Autowired
	private BoardRepository boardRepository;

	/** JPA Method Test **/

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

	/** Query Method Test **/

	@Test
	public void createBoardList() {
		Random random = new Random();
		String[] names = { "홍길동", "이순신", "유관순" };
		for (int i = 1; i <= 10; i++) {
			BoardEntity board = new BoardEntity();
			board.setTitle("테스트 제목 " + i);
			board.setWriter(names[random.nextInt(3)]);
			board.setContent("테스트 내용 " + i);
			board.setCnt(random.nextInt(20));
			boardRepository.save(board);
		}
	}

	@Test
	@DisplayName("작성자(writer) 조건으로 조회가 정상 동작한다.")
	public void findByWriterTest() {
		this.createBoardList();
		List<BoardEntity> boardList = boardRepository.findByWriter("홍길동");
		boardList.forEach(System.out::println);
	}

	@Test
	@DisplayName("제목(title) 일부 조건으로 조회가 정상 동작한다.")
	public void findByTitleContainingTest() {
		List<BoardEntity> boardList = boardRepository.findByTitleContaining("테스트");
		boardList.forEach(System.out::println);
	}

	@Test
	@DisplayName("제목(title) or 내용(content) 조건으로 조회가 정상 동작한다.")
	public void findByTitleOrContentContainingTest() {
		List<BoardEntity> boardList = boardRepository.findByTitleOrContent("테스트 제목 1", "테스트 내용 2");
		boardList.forEach(System.out::println);
	}

	@Test
	@DisplayName("조회수(cnt) 크기 비교 조건으로 조회가 정상 동작한다.")
	public void findByCntGreaterThanTest() {
		List<BoardEntity> boardList = boardRepository.findByCntGreaterThan(5);
		boardList.forEach(System.out::println);
	}

	@Test
	@DisplayName("조회수(cnt) 범위 지정 조건으로 조회가 정상 동작한다.")
	public void findByCntBetweenTest() {
		List<BoardEntity> boardList = boardRepository.findByCntBetween(5, 10);
		boardList.forEach(System.out::println);
	}

	@Test
	@DisplayName("작성자(writer) In 조건으로 조회가 정상 동작한다.")
	public void findByWriterInTest() {
		List<String> writers = Arrays.asList("이순신", "유관순");
		List<BoardEntity> boardList = boardRepository.findByWriterIn(writers);
		boardList.forEach(System.out::println);
	}

	@Test
	@DisplayName("exists~By : 특정 데이터가 존재하는지 확인한다")
	public void existsByWriterTest() {
		assertTrue(boardRepository.existsByWriter("홍길동"));
	}

	@Test
	@DisplayName("조회 쿼리를 수행한 후 쿼리 결과로 나온 레코드수를 리턴한다")
	public void countByWriterTest() {
		assertThat(boardRepository.countByWriter("홍길동")).isEqualTo(1);
	}

	@Test
	@Transactional
	@DisplayName("삭제쿼리 수행. 리턴값이 없거나 삭제한 횟수 리턴한다")
	public void deleteByWriterTest() {
		boardRepository.deleteByWriter("이순신");
		assertThat(boardRepository.existsByWriter("이순신")).isFalse();
	}

	@Test
	@Transactional
	@Rollback(false)
	@DisplayName("삭제쿼리 수행. 리턴값이 없거나 삭제한 횟수 리턴한다")
	public void deleteByWriterTest2() {
		boardRepository.deleteByWriter("이순신");
		assertThat(boardRepository.existsByWriter("이순신")).isFalse();
	}

	@Test
	@DisplayName("조회수(cnt) 내림차순 조건으로 조회가 정상 동작한다.")
	public void findByWriterOrderByCntDescTest() {
		List<BoardEntity> boardList = boardRepository.findByWriterOrderByCntDesc("홍길동");
		boardList.forEach(System.out::println);
	}

	/** Data 정렬 Test **/

	@Test
	@DisplayName("Sort 매개변수 정렬 조건이 정상 동작한다.")
	public void findWriter() {
		List<BoardEntity> boardList = boardRepository.findByWriter("홍길동", Sort.by(Sort.Direction.DESC, "cnt"));
		boardList.forEach(System.out::println);
	}

	@Test
	@DisplayName("Sort 매개변수 2차 정렬 조건이 정상 동작한다.")
	public void findAllTest() {
		List<BoardEntity> boardList = boardRepository.findAll(Sort.by(Sort.Order.desc("cnt"), Sort.Order.asc("seq")));
		boardList.forEach(System.out::println);
	}

	/** Paging 처리 Test **/

	@Test
	@DisplayName("Paging 처리 조건이 정상 동작한다.")
	public void findAllPagingTest() {
//		Page<BoardEntity> pages = boardRepository.findAll(PageRequest.of(0, 5));
		Page<BoardEntity> pages = boardRepository.findAll(PageRequest.of(0, 5, Sort.Direction.DESC, "cnt"));
		pages.forEach(System.out::println);

		System.out.println(pages);
	}
	
	/** JPQL  **/

	@Test
	public void boardQuery1Test() {
		List<BoardEntity> boardList = boardRepository.boardQuery1("테스트");
		boardList.forEach(System.out::println);
	}

	@Test
	public void boardQuery2Test() {
		List<Object[]> boardList = boardRepository.boardQuery2("테스트");
		boardList.forEach((row) -> {
			System.out.println(Arrays.toString(row));
		});
	}

}

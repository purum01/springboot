package com.edu.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.edu.dto.BoardDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	public BoardDto saveBoardDto() {
		BoardDto BoardDto = new BoardDto();
		BoardDto.setTitle("Spring Boot");
		BoardDto.setWriter("오정임");
		BoardDto.setContent("Spring을 더 쉽게 이용하기 위한 도구이다.");
		return BoardDto;
	}

	@Test
	@DisplayName("게시글 등록 테스트")
	public void insertBoardTest() throws Exception {
		BoardDto boardDto = this.saveBoardDto();
		String content = objectMapper.writeValueAsString(boardDto);
		mockMvc.perform(post("/board/insert")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
		        .andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시글 수정 테스트")
	public void updateBoardTest() throws Exception {
		BoardDto boardDto = new BoardDto();
		boardDto.setSeq(1L);
		boardDto.setTitle("Spring");
		boardDto.setContent("MVC 기반의 웹 프레임워크이다");

		mockMvc.perform(put("/board/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(boardDto)))
		        .andExpect(status().isOk())
		        .andDo(print());
	}

	@Test
	@DisplayName("게시글 삭제 테스트")
	public void deleteBoardTest() throws Exception {
		mockMvc.perform(delete("/board/delete/3"))
		       .andExpect(status().isOk())
		       .andDo(print());
	}

	@Test
	@DisplayName("특정 게시글 조회 테스트")
	public void getBoardTest() throws Exception {
		mockMvc.perform(get("/board/get/2"))
		       .andExpect(status().isOk())
		       .andDo(print());

	}

	@Test
	@DisplayName("모든 게시글 조회 테스트")
	public void getBoardListTest() throws Exception {
		mockMvc.perform(get("/board/list"))
		       .andExpect(status().isOk())
		       .andDo(print());

	}
}
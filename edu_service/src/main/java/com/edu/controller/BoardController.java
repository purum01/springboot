package com.edu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.dto.BoardDto;
import com.edu.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequestMapping("/board")
@RestController
@RequiredArgsConstructor
@Tag(name = "Board Controller", description = "이것은 게시판 컨트롤러입니다")
public class BoardController {

	private final BoardService boardService;

	@PostMapping(value = "/insert")
	@Operation(summary = "board create", description = "게시판에 새글을 등록합니다.")
	public void insertBoard(@RequestBody BoardDto dto) {
		boardService.insertBoard(dto);
	}

	@PutMapping(value = "/update")
	public void updateBoard(@RequestBody BoardDto dto) {
		boardService.updateBoard(dto);
	}

	@DeleteMapping(value = "/delete/{seq}")
	public void deleteBoard(@PathVariable Long seq) {
		boardService.deleteBoard(seq);
	}

	@GetMapping(value = "/get/{seq}")
	public BoardDto getBoard(@PathVariable Long seq) {
		return boardService.getBoard(seq);
	}

	@GetMapping(value = "/list")
	public List<BoardDto> getBoardList() {
		return boardService.getBoardList();
	}

}
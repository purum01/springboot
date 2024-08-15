package com.edu.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.edu.dto.BoardDto;
import com.edu.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController2 {

	private final BoardService boardService;

	@GetMapping(value = "/")
	public String main() {
		return "redirect:/getBoardList";
	}

	@GetMapping(value = "/insertBoard")
	public String boardForm(Model model) {
		model.addAttribute("boardDto", new BoardDto());
		return "boardForm2";
	}

//	@PostMapping(value = "/insertBoard")
//	public String insertBoard(BoardDto dto) {
//		boardService.insertBoard(dto);
//		return "redirect:/getBoardList";
//	}
	
	@PostMapping(value = "/insertBoard")
	public String insertBoard(@Valid BoardDto dto, BindingResult bindResult ) {
		if(bindResult.hasErrors()) {
			return "boardForm2";
		}
		boardService.insertBoard(dto);
		return "redirect:/getBoardList";
	}
	

	@PostMapping(value = "/updateBoard")
	public String updateBoard(BoardDto dto) {
		boardService.updateBoard(dto);
		return "redirect:/getBoardList";
	}

	@GetMapping(value = "/deleteBoard/{seq}")
	public String deleteBoard(@PathVariable Long seq) {
		boardService.deleteBoard(seq);
		return "redirect:/getBoardList";
	}

	@GetMapping(value = "/getBoard/{seq}")
	public String getBoard(@PathVariable Long seq, Model model) {
		BoardDto board = boardService.getBoard(seq);
		model.addAttribute("board", board);
		return "getBoard";
	}

	@GetMapping(value = "/getBoardList")
	public String getBoardList(Model model) {
		List<BoardDto> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);
		return "getBoardList";
	}

}

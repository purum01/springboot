package com.edu.service;

import java.util.List;

import com.edu.dto.BoardDto;

public interface BoardService {

	Long insertBoard(BoardDto dto);

	Long updateBoard(BoardDto dto);

	void deleteBoard(Long seq);

	BoardDto getBoard(Long seq);

	List<BoardDto> getBoardList();
}

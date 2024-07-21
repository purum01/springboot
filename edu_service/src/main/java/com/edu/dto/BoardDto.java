package com.edu.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.entity.Board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDto {
	private Long seq;
	private String title;
	private String writer;
	private String content;
	private LocalDateTime regDate;

	
	private static ModelMapper modelMapper = new ModelMapper();

	public Board createBoard() {
		return modelMapper.map(this, Board.class);
	}

	public static BoardDto of(Board board) {
		return modelMapper.map(board, BoardDto.class);
	}

}

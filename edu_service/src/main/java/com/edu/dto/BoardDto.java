package com.edu.dto;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.entity.Board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDto {
	private Long seq;
	
	@NotBlank(message = "제목을 입력해 주세요")
	private String title;
	private String writer;
	
	@NotEmpty(message = "내용을 입력해주세요")
	@Length(min = 5, max=50, message = "내용은 5글자 이상, 50글자 이하로 입력해주세요")
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
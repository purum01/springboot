package com.edu.board.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardVO {
	private int seq;
	private String title;
	private String writer;
	private String content;
	private Timestamp regDate;
	private int cnt;

}
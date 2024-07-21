package com.edu.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.edu.dto.BoardDto;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "board3")
@EntityListeners(AuditingEntityListener.class)
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	private String title;
	private String writer;
	private String content;

	@CreatedDate
	private LocalDateTime regDate;

	public void updateBoard(BoardDto dto) {
		this.title = dto.getTitle();
		this.content = dto.getContent();
	}

}

package com.edu.service;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.dto.BoardDto;
import com.edu.entity.Board;
import com.edu.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepo;

	public Long insertBoard(BoardDto dto) {
		Board board = dto.createBoard();
		Board savedBoard = boardRepo.save(board);
		return savedBoard.getSeq();
	}

	public Long updateBoard(BoardDto dto) {
		Board board = boardRepo.findById(dto.getSeq()).orElseThrow(EntityNotFoundException::new);
		board.updateBoard(dto);
		return board.getSeq();
	}

	public void deleteBoard(Long seq) {
		Board board = boardRepo.findById(seq).orElseThrow(EntityNotFoundException::new);
		boardRepo.delete(board);
	}

	@Transactional(readOnly = true)
	public BoardDto getBoard(Long seq) {
		Board board = boardRepo.findById(seq).orElseThrow(EntityNotFoundException::new);
		return BoardDto.of(board);
	}

	@Transactional(readOnly = true)
	public List<BoardDto> getBoardList() {
		List<Board> boardList = boardRepo.findAll();
		List<BoardDto> boardDtoList = new ArrayList<>();
		for (Board board : boardList) {
			BoardDto boardDto = BoardDto.of(board);
			boardDtoList.add(boardDto);
		}
		return boardDtoList;
	}
}

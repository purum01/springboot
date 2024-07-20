package com.edu.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.edu.board.vo.BoardVO;

@Mapper
public interface BoardMybatis {

	@Insert("INSERT INTO board (title, writer, content) VALUES(#{title},#{writer},#{content})")
	public void insertBoard(BoardVO vo);

	@Update("UPDATE board SET title=#{title}, content=#{content} WHERE seq = #{seq}")
	public void updateBoard(BoardVO vo);

	@Delete("DELETE board WHERE seq = #{seq}")
	public void deleteBoard(BoardVO vo);

	@Select("SELECT * FROM board WHERE seq = #{seq}")
	public BoardVO getBoard(BoardVO vo);

	@Select("SELECT * FROM board order by seq desc")
	public List<BoardVO> getBoardList(BoardVO vo);

}

package com.edu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	
	/** Query Method  **/
	List<BoardEntity> findByWriter(String writer);

	List<BoardEntity> findByTitleContaining(String title);

	List<BoardEntity> findByTitleOrContent(String title, String content);

	List<BoardEntity> findByCntGreaterThan(int cnt);

	List<BoardEntity> findByCntBetween(int start, int end);

	List<BoardEntity> findByWriterIn(List<String> writers);

	boolean existsByWriter(String writer);

	long countByWriter(String writer);

	void deleteByWriter(String writer);

	long removeByWriter(String writer);

	List<BoardEntity> findByWriterOrderByCntDesc(String writer);

	List<BoardEntity> findByWriter(String writer, Sort sort);

	Page<BoardEntity> findAll(Pageable pageable);

	/** JPQL  **/
	@Query("select b from BoardEntity b where b.title like %:keyword% order by b.cnt desc")
	List<BoardEntity> boardQuery1(String keyword);

	@Query(value = "select seq, title, writer from board where title like '%'||?1||'%' order by seq desc", 
	       nativeQuery = true)
	List<Object[]> boardQuery2(String keyword);


}

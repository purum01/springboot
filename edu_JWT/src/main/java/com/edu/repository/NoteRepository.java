package com.edu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edu.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

	@EntityGraph(attributePaths = "writer", type = EntityGraph.EntityGraphType.LOAD)
	@Query("select n from Note n where n.num = :num")
	Optional<Note> getWithWriter(Long num);

	@EntityGraph(attributePaths = { "writer" }, type = EntityGraph.EntityGraphType.LOAD)
	@Query("select n from Note n where n.writer.email = :email")
	List<Note> getList(String email);

}

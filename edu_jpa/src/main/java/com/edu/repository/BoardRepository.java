package com.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

}

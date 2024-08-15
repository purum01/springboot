package com.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

}

package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query(value = "select b from Board b",
            countQuery = "select count(b) from Board b")
    Page<Board> findByCat_code(String cat_code, Pageable pageable);
}

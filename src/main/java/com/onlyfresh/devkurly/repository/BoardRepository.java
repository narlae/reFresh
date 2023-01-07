package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.board.Board;
import com.onlyfresh.devkurly.domain.board.ReviewBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query(value = "select b from Board b",
            countQuery = "select count(b) from Board b")
    Page<ReviewBoard> findByPdt_id(Long pdtId, Pageable pageable);

    Optional<Board> findBoardByBbsId(Long bbsId);

}

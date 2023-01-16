package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.board.Board;
import com.onlyfresh.devkurly.domain.board.ReviewBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "select b from Board b where b.product.pdtId = :pdtId ",
            countQuery = "select count(b) from Board b where b.product.pdtId = :pdtId")
    Page<ReviewBoard> findByPdt_id(@Param("pdtId") Long pdtId, Pageable pageable);

    Optional<ReviewBoard> findBoardByBbsId(Long bbsId);



}

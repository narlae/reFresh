package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
//    List<Board> findBoardsByPdt_idOrderByBbs_idDesc(Integer pdt_id, Integer bbs_id);
}

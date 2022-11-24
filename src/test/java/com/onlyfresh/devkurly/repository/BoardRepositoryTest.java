package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.board.Board;
import com.onlyfresh.devkurly.domain.board.ReviewBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class BoardRepositoryTest {

    @Autowired BoardRepository boardRepository;

    @Test
    public void BoardTest() {
        for (int i = 0; i < 10; i++) {
            Board board = new ReviewBoard();
            board.setBbs_title("제목" + i);
            board.setBbs_cn("내용입니다." + i);

            boardRepository.save(board);
        }

        boardRepository.findAll();

        long count = boardRepository.count();
        assertEquals(10, count);

        boardRepository.deleteAll();
        long count2 = boardRepository.count();

        assertEquals(0, count2);
    }

    @Test
    public void BoardUpdateTest() {
        Board board = new ReviewBoard();
        board.setBbs_title("제목");
        board.setBbs_cn("내용입니다.");

        boardRepository.save(board);
        board.setBbs_title("수정된 제목");

        long count = boardRepository.count();
        assertEquals(1, count);

    }

}
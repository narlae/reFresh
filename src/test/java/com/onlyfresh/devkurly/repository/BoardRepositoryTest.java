package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.board.Board;
import com.onlyfresh.devkurly.domain.board.ReviewBoard;
import com.onlyfresh.devkurly.web.dto.ReviewBoardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    public void BoardPaging(){

        for (int i = 0; i < 120; i++) {
            Board board = new ReviewBoard();
            board.setBbs_title("제목입니다." + i);
            board.setBbs_cn("내용입니다." + i);


            boardRepository.save(board);
        }
//
//        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "bbs_id"));
//
//        Page<Board> page = boardRepository.findByCat_code("", pageRequest);
//
//        Page<ReviewBoardDto> dtoPage = page.map(m -> new ReviewBoardDto(m.getBbs_title(), m.getBbs_cn()));
//
//        List<ReviewBoardDto> content = dtoPage.getContent();
//
//        System.out.println("content = " + content);
//        assertThat(content.size()).isEqualTo(10);


//        assertThat(content.size()).isEqualTo(3);
//        assertThat(page.getTotalElements()).isEqualTo(5);
//        assertThat(page.getNumber()).isEqualTo(0);
//        assertThat(page.getTotalPages()).isEqualTo(2);
//        assertThat(page.isFirst()).isTrue();
//        assertThat(page.hasNext()).isTrue();

    }
    @Test
    public void BoardAllDelete() {
        boardRepository.deleteAll();
    }

    @Test
    public void ReviewTest(){

    }

}
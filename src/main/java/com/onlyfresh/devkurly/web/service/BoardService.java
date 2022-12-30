package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.board.Board;
import com.onlyfresh.devkurly.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void updateBoard(Integer bbs_id, String bbs_title, String bbs_cn) {
        Board board = boardRepository.findById(bbs_id).get();
        board.setBbs_title(bbs_title);
        board.setBbs_cn(bbs_cn);
    }
}

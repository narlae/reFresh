package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.board.Board;
import com.onlyfresh.devkurly.repository.BoardRepository;
import com.onlyfresh.devkurly.web.dto.BoardDto;
import com.onlyfresh.devkurly.web.dto.ReviewBoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BoardController {

    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    @GetMapping("/board")
    public String boardList(Integer totalCnt, Integer page ,Model m) {

        return "board/board";
    }

    @GetMapping("/board/{cat_code}/{page}")
    @ResponseBody
    public Page<ReviewBoardDto> getBoardList(@PathVariable String cat_code, @PathVariable Integer page, String sort_option) {

        return getList(cat_code, sort_option, page, 10);
    }

//    @GetMapping("/board/{cat_code}/{page}")
//    public String getBoardList(@PathVariable String cat_code, @PathVariable Integer page, String sort_option, Model model) {
//
//        List<ReviewBoardDto> list = getList(cat_code, sort_option, page, 10);
//        model.addAttribute("result", list);
//
//        System.out.println("list = " + list);
//        System.out.println("model = " + model);
//
//        return "board/board";
//    }

    private Page<ReviewBoardDto> getList(String cat_code, String sort_option, int page, int pageSize) {

        PageRequest pageRequest = PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.DESC, sort_option));

        Page<Board> boardPage = boardRepository.findByCat_code(cat_code, pageRequest);
        Page<ReviewBoardDto> dtoPage = boardPage.map(m -> new ReviewBoardDto(m.getBbs_id(), m.getBbs_title(), m.getBbs_cn()));

        return dtoPage;
    }
}

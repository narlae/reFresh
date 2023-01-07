package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.board.Board;
import com.onlyfresh.devkurly.domain.board.ReviewBoard;
import com.onlyfresh.devkurly.repository.BoardRepository;
import com.onlyfresh.devkurly.web.dto.ReviewBoardDto;
import com.onlyfresh.devkurly.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    public BoardController(BoardRepository boardRepository, BoardService boardService) {
        this.boardRepository = boardRepository;
        this.boardService = boardService;
    }


    @GetMapping
    public String boardList(Integer page, String sort_option) {

        return "board/board";
    }

    @GetMapping("/{pdt_id}/{page}")
    @ResponseBody
    public Page<ReviewBoardDto> getBoardList(@PathVariable Integer pdt_id, @PathVariable Integer page, String sort_option) {

        return getList(pdt_id, sort_option, page, 10);
    }

    @PostMapping("/{pdt_id}")
    @ResponseBody
    public ResponseEntity<String> write(@PathVariable Integer pdt_id, @RequestBody ReviewBoardDto reviewBoardDto) {
        Board board = new ReviewBoard();
//        board.getProduct().setPdt_id(pdt_id);
        board.setBbs_title(reviewBoardDto.getBbs_title());
        board.setBbs_cn(reviewBoardDto.getBbs_cn());
        try {
            boardRepository.save(board);
            return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{pdt_id}")
    @ResponseBody
    public ResponseEntity<String> modify(@PathVariable("pdt_id") Integer pdt_id,
                                         @RequestBody ReviewBoardDto dto) {
        try {
            boardService.updateBoard(dto.getBbs_id(), dto.getBbs_title(), dto.getBbs_cn());
            return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("MOD_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{pdt_id}/{bbs_id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable("pdt_id") Integer pdt_id,
                                         @PathVariable("bbs_id") Integer bbs_id) {

        Board board = new ReviewBoard();
        board.setBbs_id(bbs_id);

        try {
            boardRepository.delete(board);
            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }




    private Page<ReviewBoardDto> getList(Integer pdt_id, String sort_option, int page, int pageSize) {

        PageRequest pageRequest = PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.DESC, sort_option));

        Page<Board> boardPage = boardRepository.findByPdt_id(pdt_id, pageRequest);
        Page<ReviewBoardDto> dtoPage = boardPage.map(m -> new ReviewBoardDto(m.getBbs_id(), m.getBbs_title(), m.getBbs_cn()));

        return dtoPage;
    }
}

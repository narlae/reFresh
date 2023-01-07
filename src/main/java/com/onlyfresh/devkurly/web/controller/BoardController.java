package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.board.Board;
import com.onlyfresh.devkurly.domain.board.ReviewBoard;
import com.onlyfresh.devkurly.repository.BoardRepository;
import com.onlyfresh.devkurly.web.dto.ReviewBoardDto;
import com.onlyfresh.devkurly.web.dto.member.MemberMainResponseDto;
import com.onlyfresh.devkurly.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


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
    public String boardList(Long page, String sort_option) {

        return "board/board";
    }

    @GetMapping("/{pdtId}/{page}")
    @ResponseBody
    public Page<ReviewBoardDto> getBoardList(@PathVariable Long pdtId, @PathVariable int page, String sort_option) {

        return getList(pdtId, sort_option, page, 10);
    }

    @PostMapping("/{pdtId}")
    @ResponseBody
    public ResponseEntity<String> write(@PathVariable Long pdtId, @RequestBody ReviewBoardDto reviewBoardDto,
                                        HttpSession session) {
        try {
            boardService.write(session, reviewBoardDto);
            return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{pdtId}")
    @ResponseBody
    public ResponseEntity<String> modify(@PathVariable("pdtId") Long pdtId,
                                         @RequestBody ReviewBoardDto dto) {
        try {
            boardService.updateBoard(dto.getBbsId(), dto.getBbsTitle(), dto.getBbsCn());
            return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("MOD_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{pdtId}/{bbsId}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable("pdtId") Long pdtId,
                                         @PathVariable("bbsId") Long bbsId) {

        Board board = new ReviewBoard();
        board.setBbsId(bbsId);

        try {
            boardRepository.delete(board);
            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    private Page<ReviewBoardDto> getList(Long pdtId, String sort_option, int page, int pageSize) {

        PageRequest pageRequest = PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.DESC, sort_option));

        Page<ReviewBoard> boardPage = boardRepository.findByPdt_id(pdtId, pageRequest);

        Page<ReviewBoardDto> dtoPage = boardPage.map(m ->
                new ReviewBoardDto(m.getBbsId(), m.getBbsTitle(), m.getBbsCn(),
                        m.getRevwLike()));
        return dtoPage;
    }
}

package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.board.Board;
import com.onlyfresh.devkurly.domain.board.ReviewBoard;
import com.onlyfresh.devkurly.repository.BoardRepository;
import com.onlyfresh.devkurly.web.dto.ReviewBoardDto;
import com.onlyfresh.devkurly.web.exception.MemberListException;
import com.onlyfresh.devkurly.web.exception.SignInException;
import com.onlyfresh.devkurly.web.service.BoardService;
import com.onlyfresh.devkurly.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final MemberService memberService;



    @GetMapping
    public String boardList(Long page, String sort_option) {

        return "board/reviewBoard";
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
            boardService.write(pdtId, session, reviewBoardDto);
            return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{pdtId}")
    @ResponseBody
    public ResponseEntity<String> modify(@PathVariable("pdtId") Long pdtId,
                                         @RequestBody ReviewBoardDto dto,
                                         HttpSession session) {
        Long userId = getUserId(session);
        boardService.updateBoard(dto.getBbsId(), dto.getBbsTitle(), dto.getBbsCn(), userId);
        return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
    }

    @DeleteMapping("/{pdtId}/{bbsId}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable("pdtId") Long pdtId,
                                         @PathVariable("bbsId") Long bbsId,
                                         HttpSession session) {
        Long userId = getUserId(session);
        Board board = boardService.findBoardById(bbsId);
        if (!userId.equals(board.getMember().getUserId())) {
            throw new MemberListException("자신의 글만 삭제할 수 있습니다.");
        }
        try {
            boardRepository.delete(board);
            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/like/{pdtId}/{bbsId}")
    @ResponseBody
    public ResponseEntity<String> boardLike(@PathVariable("pdtId") Long pdtId,
                                            @PathVariable("bbsId") Long bbsId,
                                            HttpSession session) {
        boardService.makeLikeNo(session, bbsId).pushLike();
        boardService.likeUp(bbsId);
        return new ResponseEntity<>("LIK_OK", HttpStatus.OK);
    }

    private Long getUserId(HttpSession session) {
        return Optional.ofNullable(memberService.extractDto(session).getUserId())
                .orElseThrow(() -> new SignInException("로그인이 필요합니다."));
    }

    private Page<ReviewBoardDto> getList(Long pdtId, String sort_option, int page, int pageSize) {

        PageRequest pageRequest = PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.DESC, sort_option));

        Page<ReviewBoard> boardPage = boardRepository.findByPdt_id(pdtId, pageRequest);
        Page<ReviewBoardDto> dtoPage = boardPage.map(m ->
                new ReviewBoardDto(m));
        return dtoPage;
    }
}

package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.board.Board;
import com.onlyfresh.devkurly.domain.board.ReviewBoard;
import com.onlyfresh.devkurly.repository.BoardRepository;
import com.onlyfresh.devkurly.web.dto.ReviewBoardDto;
import com.onlyfresh.devkurly.web.exception.MemberListException;
import com.onlyfresh.devkurly.web.exception.SignInException;
import com.onlyfresh.devkurly.web.service.BoardService;
import com.onlyfresh.devkurly.web.service.MemberService;
import com.onlyfresh.devkurly.web.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final MemberService memberService;



    @GetMapping
    public String boardList(Long page, String sort_option) {

        return "board/reviewBoard";
    }

    @GetMapping("/boardlist/{pdtId}/{page}")
    @ResponseBody
    public Page<ReviewBoardDto> getBoardList(@PathVariable Long pdtId, @PathVariable int page, String sort_option) {

        return getList(pdtId, sort_option, page);
    }

    @PostMapping("/board/{pdtId}")
    @ResponseBody
    public ResponseEntity<String> write(@PathVariable Long pdtId, @RequestBody ReviewBoardDto reviewBoardDto) {
        try {
            String userEmail = SecurityUtil.getCurrentMemberId();
            boardService.write(pdtId, userEmail, reviewBoardDto);
            return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/board/{pdtId}")
    @ResponseBody
    public ResponseEntity<String> modify(@PathVariable("pdtId") Long pdtId,
                                         @RequestBody ReviewBoardDto dto) {
        String userEmail = SecurityUtil.getCurrentMemberId();
        boardService.updateBoard(dto.getBbsId(), dto.getBbsTitle(), dto.getBbsCn(), userEmail);
        return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
    }

    @DeleteMapping("/board/{pdtId}/{bbsId}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable("pdtId") Long pdtId,
                                         @PathVariable("bbsId") Long bbsId) {
        String userEmail = SecurityUtil.getCurrentMemberId();
        Long userId = memberService.findMemberByEmail(userEmail).getUserId();
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
    @GetMapping("/board/like/{pdtId}/{bbsId}")
    @ResponseBody
    public ResponseEntity<String> boardLike(@PathVariable("pdtId") Long pdtId,
                                            @PathVariable("bbsId") Long bbsId) {
        String userEmail = SecurityUtil.getCurrentMemberId();
        boardService.makeLikeNo(userEmail, bbsId).pushLike();
        boardService.likeUp(bbsId);
        return new ResponseEntity<>("LIK_OK", HttpStatus.OK);
    }

    private Page<ReviewBoardDto> getList(Long pdtId, String sort_option, int page) {

        PageRequest pageRequest = PageRequest.of(page, 10,
                Sort.by(Sort.Direction.DESC, sort_option));

        Page<ReviewBoard> boardPage = boardRepository.findByPdt_id(pdtId, pageRequest);
        return boardPage.map(ReviewBoardDto::new);
    }
}

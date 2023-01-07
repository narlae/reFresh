package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.board.Board;
import com.onlyfresh.devkurly.domain.board.ReviewBoard;
import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.repository.BoardRepository;
import com.onlyfresh.devkurly.repository.MemberRepository;
import com.onlyfresh.devkurly.web.dto.ReviewBoardDto;
import com.onlyfresh.devkurly.web.dto.member.MemberMainResponseDto;
import com.onlyfresh.devkurly.web.exception.BoardListException;
import com.onlyfresh.devkurly.web.exception.MemberListException;
import com.onlyfresh.devkurly.web.exception.SignInException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void write(HttpSession session, ReviewBoardDto boardDto) {
        Long user_id = extractDto(session).getUserId();
        Member member = memberRepository.findById(user_id)
                .orElseThrow(() -> new MemberListException("존재하지 않는 회원입니다."));
        Board board = getBoard(boardDto, user_id, member);
        boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(Long bbsId, String bbsTitle, String bbsCn) {
        Board board = boardRepository.findBoardByBbsId(bbsId)
                .orElseThrow(() -> new BoardListException("해당 글이 존재하지 않습니다."));
        board.setBbsTitle(bbsTitle);
        board.setBbsCn(bbsCn);
    }

    private MemberMainResponseDto extractDto(HttpSession session) {
        return ((MemberMainResponseDto) session.getAttribute("loginMember"));
    }

    private Board getBoard(ReviewBoardDto reviewBoardDto, Long user_id, Member member) {
        Board board = new ReviewBoard();
        board.setBbsTitle(reviewBoardDto.getBbsTitle());
        board.setBbsCn(reviewBoardDto.getBbsCn());
        board.setMember(member);
        return board;
    }
}

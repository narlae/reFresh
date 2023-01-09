package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.board.Board;
import com.onlyfresh.devkurly.domain.board.MemberLikeNo;
import com.onlyfresh.devkurly.domain.board.ReviewBoard;
import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.repository.BoardRepository;
import com.onlyfresh.devkurly.repository.MemberLikeNoRepository;
import com.onlyfresh.devkurly.repository.MemberRepository;
import com.onlyfresh.devkurly.web.dto.ReviewBoardDto;
import com.onlyfresh.devkurly.web.dto.member.MemberMainResponseDto;
import com.onlyfresh.devkurly.web.exception.BoardListException;
import com.onlyfresh.devkurly.web.exception.LikeNoException;
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
    private final MemberLikeNoRepository likeNoRepository;
    private final MemberService memberService;


    public void write(HttpSession session, ReviewBoardDto boardDto) {
        Long user_id = extractDto(session).getUserId();
        Member member = memberRepository.findById(user_id)
                .orElseThrow(() -> new MemberListException("존재하지 않는 회원입니다."));
        ReviewBoard board = getBoard(boardDto, user_id, member);
        board.setRevwLike(0);
        boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(Long bbsId, String bbsTitle, String bbsCn) {
        Board board = boardRepository.findBoardByBbsId(bbsId)
                .orElseThrow(() -> new BoardListException("해당 글이 존재하지 않습니다."));
        board.setBbsTitle(bbsTitle);
        board.setBbsCn(bbsCn);
    }

    public Board findBoardById(Long bbsId) {
        return boardRepository.findById(bbsId).orElseThrow(() -> new BoardListException("존재하지 않는 글입니다."));
    }

    @Transactional
    public MemberLikeNo makeLikeNo(HttpSession session, Long bbsId) {
        Long userId = extractDto(session).getUserId();
        Member member = memberService.findMemberById(userId);
        Board board = findBoardById(bbsId);
        if (userId.equals(board.getMember().getUserId())) {
            throw new LikeNoException("자신의 글에 추천할 수 없습니다.");
        }
        MemberLikeNo memberLikeNo = likeNoRepository.findByBoardAndMember(board, member)
                .orElseGet(() -> new MemberLikeNo(board, member));
        likeNoRepository.save(memberLikeNo);
        return memberLikeNo;
    }

    @Transactional
    public void likeUp(Long bbsId) {
        ReviewBoard board = boardRepository.findBoardByBbsId(bbsId)
                .orElseThrow(() -> new BoardListException("존재하지 않는 글입니다."));
        board.increaseRevwLike();
    }

    private MemberMainResponseDto extractDto(HttpSession session) {
        return ((MemberMainResponseDto) session.getAttribute("loginMember"));
    }

    private ReviewBoard getBoard(ReviewBoardDto reviewBoardDto, Long user_id, Member member) {
        ReviewBoard board = new ReviewBoard();
        board.setBbsTitle(reviewBoardDto.getBbsTitle());
        board.setBbsCn(reviewBoardDto.getBbsCn());
        board.setMember(member);

        return board;
    }
}

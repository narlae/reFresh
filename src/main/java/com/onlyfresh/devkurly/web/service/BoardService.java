package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.board.Board;
import com.onlyfresh.devkurly.domain.board.MemberLikeNo;
import com.onlyfresh.devkurly.domain.board.ReviewBoard;
import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.domain.product.Product;
import com.onlyfresh.devkurly.repository.BoardRepository;
import com.onlyfresh.devkurly.repository.MemberLikeNoRepository;
import com.onlyfresh.devkurly.repository.MemberRepository;
import com.onlyfresh.devkurly.repository.ProductRepository;
import com.onlyfresh.devkurly.web.dto.ReviewBoardDto;
import com.onlyfresh.devkurly.web.exception.BoardListException;
import com.onlyfresh.devkurly.web.exception.LikeNoException;
import com.onlyfresh.devkurly.web.exception.MemberListException;
import com.onlyfresh.devkurly.web.exception.NotFoundDBException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberLikeNoRepository likeNoRepository;
    private final MemberService memberService;
    private final ProductService productService;


    public void write(Long pdtId, HttpSession session, ReviewBoardDto boardDto) {
        Long user_id = memberService.extractDto(session).getUserId();
        Member member = memberService.findMemberById(user_id);
        Product product = productService.findProductById(pdtId);
        ReviewBoard board = getBoard(boardDto, member, product);
        board.setRevwLike(0);
        boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(Long bbsId, String bbsTitle, String bbsCn, Long userId) {
        Board board = findBoardById(bbsId);
        if (!userId.equals(board.getMember().getUserId())) {
            throw new MemberListException("자신의 글만 수정할 수 있습니다.");
        }
        board.setBbsTitle(bbsTitle);
        board.setBbsCn(bbsCn);
    }

    @Transactional
    public MemberLikeNo makeLikeNo(HttpSession session, Long bbsId) {
        Long userId = memberService.extractDto(session).getUserId();
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

    public Board findBoardById(Long bbsId) {
        return boardRepository.findById(bbsId).orElseThrow(() -> new BoardListException("존재하지 않는 글입니다."));
    }

    private ReviewBoard getBoard(ReviewBoardDto reviewBoardDto,  Member member, Product product) {
        ReviewBoard board = new ReviewBoard();
        board.setBbsTitle(reviewBoardDto.getBbsTitle());
        board.setBbsCn(reviewBoardDto.getBbsCn());
        board.setMember(member);
        board.setProduct(product);

        return board;
    }
}

package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.board.Board;
import com.onlyfresh.devkurly.domain.board.MemberLikeNo;
import com.onlyfresh.devkurly.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberLikeNoRepository extends JpaRepository<MemberLikeNo, Long> {

    Optional<MemberLikeNo> findByBoardAndMember(Board board, Member member);


}

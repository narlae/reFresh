package com.onlyfresh.devkurly.domain.board;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.web.exception.LikeNoException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MemberLikeNo {

    @Id
    @GeneratedValue
    @Column(name = "likeId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bbsId")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Member member;

    public MemberLikeNo(Board board, Member member) {
        this.board = board;
        this.member = member;
    }

    private boolean isPushLike = false;

    public void setMemberNBoard(Member member, Board board) {
        this.member = member;
        this.board = board;
    }

    public void pushLike() {
        if (!this.isPushLike) {
            this.isPushLike = true;
        } else {
            throw new LikeNoException("이미 추천한 글입니다.");
        }
    }
}

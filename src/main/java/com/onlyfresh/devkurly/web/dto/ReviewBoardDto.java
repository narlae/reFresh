package com.onlyfresh.devkurly.web.dto;

import com.onlyfresh.devkurly.domain.board.ReviewBoard;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ReviewBoardDto {
    private Long bbsId;
    private String userNm;
    private String catCode; //카테고리 코드
    private String bbsTitle;
    private LocalDateTime wrtDt;
    private String bbsCn;
    private Integer revwLike;
    private String revwImg;

    public ReviewBoardDto() {
    }

    public ReviewBoardDto(ReviewBoard reviewBoard) {
        this.bbsId = reviewBoard.getBbsId();
        this.userNm = reviewBoard.getMember().getUserNm();
        this.bbsTitle = reviewBoard.getBbsTitle();
        this.wrtDt = reviewBoard.getWrtDt();
        this.bbsCn = bbsCn;
        this.revwLike = reviewBoard.getRevwLike();
        this.revwImg = reviewBoard.getRevwImg();
    }
}

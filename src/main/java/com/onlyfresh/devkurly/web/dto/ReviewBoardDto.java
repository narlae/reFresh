package com.onlyfresh.devkurly.web.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewBoardDto {
    private Long bbsId;
    private String userNm;
    private String catCode; //카테고리 코드
    private String bbsTitle;
    private Date wrtDt;
    private String bbsCn;
    private Integer revwLike;
    private Integer likeNo;
    private String revwImg;

    public ReviewBoardDto(Long bbsId, String bbsTitle, String bbsCn, Integer revwLike) {
        this.bbsId = bbsId;
        this.bbsTitle = bbsTitle;
        this.bbsCn = bbsCn;
        this.revwLike = revwLike;
    }
}

package com.onlyfresh.devkurly.web.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewBoardDto {
    private Integer bbs_id;
    private String user_nm;
    private String cat_code; //카테고리 코드
    private String bbs_title;
    private Date wrt_dt;
    private String bbs_cn;
    private Integer revw_like;
    private Integer like_no;
    private String revw_img;

    public ReviewBoardDto(Integer bbs_id, String bbs_title, String bbs_cn) {
        this.bbs_id = bbs_id;
        this.bbs_title = bbs_title;
        this.bbs_cn = bbs_cn;
    }
}

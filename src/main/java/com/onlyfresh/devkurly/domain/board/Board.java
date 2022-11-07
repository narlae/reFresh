package com.onlyfresh.devkurly.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue
    private Integer bbs_id;

    private Integer pdt_id;
    private Integer user_id;
    private String bbs_title;
    private Date wrt_dt;
    private String user_nm;
    private String bbs_clsf_cd;
    private char notice;



    private String bbs_cn;
    private Integer revw_like;
    private Integer like_no;
    private String revw_img;
    private boolean is_secret;
    private boolean is_replied;

    private Date in_date;
    private String in_user;
    private Date up_date;
    private String up_user;
}

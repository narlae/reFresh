package com.onlyfresh.devkurly.web.dto.order;

import lombok.Data;

import java.util.Date;

@Data
public class KakaoPayReadyVO {

    //response
    private String tid;
    private String next_redirect_pc_url;
    private Date created_at;

}
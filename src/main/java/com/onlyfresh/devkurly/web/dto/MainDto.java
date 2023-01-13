package com.onlyfresh.devkurly.web.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MainDto {
    private Long pdtId;
    private String image;
    private String title;
    private Integer dsRate;
    private Integer selPrice;
    private Integer price;
}

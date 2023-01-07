package com.onlyfresh.devkurly.web.dto;

import lombok.Data;

@Data
public class BoardDto {
    private String catCode;
    private String sortOption;
    private int page = 0;

}

package com.onlyfresh.devkurly.web.dto;

import lombok.Data;

@Data
public class BoardDto {
    private String cat_code;
    private String sort_option;
    private int page = 0;

}

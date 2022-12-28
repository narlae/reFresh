package com.onlyfresh.devkurly.web.dto;

import lombok.Data;

@Data
public class BoardDto {
    String cat_code;
    String sort_option;
    int page = 0;

}

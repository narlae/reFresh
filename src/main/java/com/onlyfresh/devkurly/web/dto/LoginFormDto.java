package com.onlyfresh.devkurly.web.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginFormDto {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
}

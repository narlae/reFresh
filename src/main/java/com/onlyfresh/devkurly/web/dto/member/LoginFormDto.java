package com.onlyfresh.devkurly.web.dto.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginFormDto {
    @NotEmpty
    private String userEmail;
    @NotEmpty
    private String pwd;
}

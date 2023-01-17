package com.onlyfresh.devkurly.web.dto.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginFormDto {
    @NotEmpty(message = "이메일은 필수입니다.")
    private String userEmail;
    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String pwd;
}

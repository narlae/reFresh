package com.onlyfresh.devkurly.web.dto.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterFormDto {
    @NotEmpty
    private String user_email;
    @NotEmpty
    private String pwd;
    @NotEmpty
    private String user_nm;
    @NotEmpty
    private String telno;


    private String rcmdr_email;
    private String gender;
    private Character prvc_arge; //선택 이용 약관 동의


}

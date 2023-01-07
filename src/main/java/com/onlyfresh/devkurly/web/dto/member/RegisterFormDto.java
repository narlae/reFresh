package com.onlyfresh.devkurly.web.dto.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterFormDto {
    @NotEmpty
    private String userEmail;
    @NotEmpty
    private String pwd;
    @NotEmpty
    private String userNm;
    @NotEmpty
    private String telno;


    private String rcmdrEmail;
    private String gender;
    private Character prvcArge; //선택 이용 약관 동의


}

package com.onlyfresh.devkurly.web.dto.member;

import com.onlyfresh.devkurly.web.utils.validator.ConfirmPwd;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@ConfirmPwd(pwd = "pwd", cpwd = "cpwd")
public class RegisterForm {
    @NotEmpty(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식으로 써주세요.")
    private String userEmail;
    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String pwd;
    @NotEmpty(message = "비밀번호 확인은 필수입니다.")
    private String cpwd;
    @NotEmpty(message = "성함은 필수입니다.")
    private String userNm;
    @NotEmpty(message = "전화번호는 필수입니다.")
    private String telno;

    @NotEmpty(message = "주소 입력은 필수입니다. 주소를 검색해주세요.")
    private String zoneCode;
    @NotEmpty(message = "주소 입력은 필수입니다. 주소를 검색해주세요.")
    private String address;
    @NotEmpty(message = "세부 주소도 입력해주세요.")
    private String addressDetail;

    @Email(message = "이메일 형식으로 써주세요.")
    private String rcmdrEmail;
    private String gender;
    private boolean prvcArge; //선택 이용 약관 동의


}

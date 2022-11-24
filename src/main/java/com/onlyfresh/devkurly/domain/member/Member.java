package com.onlyfresh.devkurly.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer user_id;

    @NotEmpty
    private String user_nm;

    @NotEmpty
    private String user_email;

    @NotEmpty
    private String pwd;

    private String telno;

    private Date subs_dt;

    private String gender;
    //    private Date bryr;
    private Character prvc_arge; //선택 이용 약관 동의
    private Character user_cls_cd; //유저 분류 코드
    private String rcmdr_email; //추천인 이메일
    private Integer pnt; //페이 포인트

    @Builder
    public Member(String user_nm, String user_email, String pwd) {
        this.user_nm = user_nm;
        this.user_email = user_email;
        this.pwd = pwd;
    }
}
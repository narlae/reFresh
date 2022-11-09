package com.onlyfresh.devkurly.domain.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Getter @Setter
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
    private Character prvc_arge;
    private Character user_cls_cd;
    private String rcmdr_email;
    private Integer pnt;

}
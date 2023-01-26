package com.onlyfresh.devkurly.web.dto.member;

import com.onlyfresh.devkurly.domain.member.Member;
import lombok.Data;

@Data
public class OrderMemberForm {

    private String userNm;
    private String telno;
    private String userEmail;

    public OrderMemberForm(Member member) {
        this.userNm = member.getUserNm();
        this.telno = member.getTelno();
        this.userEmail = member.getUserEmail();
    }
}

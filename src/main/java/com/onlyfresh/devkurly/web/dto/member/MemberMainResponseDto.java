package com.onlyfresh.devkurly.web.dto.member;

import com.onlyfresh.devkurly.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberMainResponseDto {
    private Long userId;
    private String userNm;
    private Character userClsCd;

    public MemberMainResponseDto(Member member) {
        this.userId = member.getUserId();
        this.userNm = member.getUserNm();
        this.userClsCd = member.getUserClsCd();
    }
}

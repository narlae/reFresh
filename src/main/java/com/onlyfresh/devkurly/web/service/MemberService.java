package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.repository.MemberRepository;
import com.onlyfresh.devkurly.web.dto.member.LoginFormDto;
import com.onlyfresh.devkurly.web.dto.member.MemberMainResponseDto;
import com.onlyfresh.devkurly.web.dto.member.RegisterFormDto;
import com.onlyfresh.devkurly.web.exception.LoginException;
import com.onlyfresh.devkurly.web.exception.MemberListException;
import com.onlyfresh.devkurly.web.exception.SignInException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberMainResponseDto checkMember(LoginFormDto loginFormDto) {
        Optional<Member> optionalMember =
                memberRepository.findMemberByUserEmailAndPwd(loginFormDto.getUserEmail());
        optionalMember.orElseThrow(() -> new SignInException("존재하지 않는 회원입니다."));
        Member member = optionalMember.get();
        checkPwd(member, loginFormDto.getPwd());
        return new MemberMainResponseDto(member);
    }

    public MemberMainResponseDto registerMember(RegisterFormDto formDto) {
        Member member = memberBuild(formDto);
        memberRepository.save(member);
        return new MemberMainResponseDto(member);
    }

    public Member findMemberById(Long userId) {
        return memberRepository.findById(userId).orElseThrow(() -> new MemberListException("존재하지 않는 회원입니다."));
    }

    public MemberMainResponseDto extractDto(HttpSession session) {
        return (MemberMainResponseDto) Optional.ofNullable(session.getAttribute("loginMember"))
                .orElseThrow(() -> new LoginException("로그인이 필요합니다."));

    }

    private Member memberBuild(RegisterFormDto formDto) {
        return Member.builder()
                .userEmail(formDto.getUserEmail())
                .pwd(formDto.getPwd())
                .userNm(formDto.getUserNm())
                .telno(formDto.getTelno())
                .rcmdrEmail(formDto.getRcmdrEmail())
                .gender(formDto.getGender())
                .prvcArge(formDto.getPrvcArge())
                .build();
    }

    private void checkPwd(Member member, String pwd) {
        if (!member.getPwd().equals(pwd)) {
            throw new SignInException("아이디 또는 비밀번호가 틀립니다.");
        }
    }
}

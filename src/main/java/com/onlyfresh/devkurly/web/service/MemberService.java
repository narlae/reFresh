package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.repository.MemberRepository;
import com.onlyfresh.devkurly.web.dto.member.LoginFormDto;
import com.onlyfresh.devkurly.web.dto.member.MemberMainResponseDto;
import com.onlyfresh.devkurly.web.exception.LoginException;
import com.onlyfresh.devkurly.web.exception.SignInException;
import org.springframework.stereotype.Service;

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
                memberRepository.findMemberByUser_emailAndPwd(loginFormDto.getUser_email());
        optionalMember.orElseThrow(() -> new SignInException("존재하지 않는 회원입니다."));
        Member member = optionalMember.get();
        checkPwd(member, loginFormDto.getPwd());
        return new MemberMainResponseDto(member);
    }

    private void checkPwd(Member member, String pwd) {
        if (!member.getPwd().equals(pwd)) {
            throw new SignInException("아이디 또는 비밀번호가 틀립니다.");
        }
    }
}
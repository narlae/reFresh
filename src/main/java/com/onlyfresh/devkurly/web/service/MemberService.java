package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.repository.MemberRepository;
import com.onlyfresh.devkurly.web.exception.SignInException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void checkMember(String user_email, String pwd) {
        Optional<Member> optionalMember = memberRepository.findMemberByUser_emailAndPwd(user_email, pwd);
        optionalMember.orElseThrow(() -> new SignInException("존재하지 않는 회원입니다."));
    }
}

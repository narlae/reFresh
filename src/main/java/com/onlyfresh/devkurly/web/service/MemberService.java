package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.Address;
import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.domain.member.MemberAuthoritiesCode;
import com.onlyfresh.devkurly.domain.member.MemberAuthoritiesMapping;
import com.onlyfresh.devkurly.repository.MemberAuthoritiesCodeRepository;
import com.onlyfresh.devkurly.repository.MemberAuthoritiesMappingRepository;
import com.onlyfresh.devkurly.repository.MemberRepository;
import com.onlyfresh.devkurly.web.dto.member.LoginFormDto;
import com.onlyfresh.devkurly.web.dto.member.MemberMainResponseDto;
import com.onlyfresh.devkurly.web.dto.member.RegisterForm;
import com.onlyfresh.devkurly.web.exception.*;
import com.onlyfresh.devkurly.web.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberAuthoritiesMappingRepository mappingRepository;
    private final MemberAuthoritiesCodeRepository codeRepository;

    public Collection<? extends GrantedAuthority> getAuthorities(Member member) {
        String[] userRoles =  convert(member.getMemberAuthoritiesMappingList());
        return AuthorityUtils.createAuthorityList(userRoles);
    }
    public String[] convert(List<MemberAuthoritiesMapping> list) {
        return list.stream()
                .map(mapping -> mapping.getMemberAuthoritiesCode().getAuthority()).toArray(String[]::new);
    }

    public MemberMainResponseDto checkMember(LoginFormDto loginFormDto) throws LoginFormCheckException{
        Optional<Member> optionalMember =
                memberRepository.findMemberByUserEmailAndPwd(loginFormDto.getUserEmail());
        optionalMember.orElseThrow(() -> new LoginFormCheckException("아이디 또는 비밀번호가 틀립니다."));
        Member member = optionalMember.get();
        checkPwd(member, loginFormDto.getPwd());
        return new MemberMainResponseDto(member);
    }

    @Transactional
    public Member registerMember(RegisterForm formDto) {
        String userEmail = formDto.getUserEmail();
        memberRepository.findMemberByUserEmailAndPwd(userEmail).ifPresent((m) -> {
            throw new MemberDuplicateException("이미 회원으로 존재하는 이메일입니다.");
        });

        Member member = memberBuild(formDto);
        MemberAuthoritiesCode memberAuthoritiesCode = new MemberAuthoritiesCode();
        memberAuthoritiesCode.setAuthority("USER");
        MemberAuthoritiesMapping mapping = MemberAuthoritiesMapping.builder()
                .member(member)
                .memberAuthoritiesCode(memberAuthoritiesCode)
                .build();
        member.getMemberAuthoritiesMappingList().add(mapping);
        mappingRepository.save(mapping);
        codeRepository.save(memberAuthoritiesCode);

        Address address = Address.of(member, formDto.getAddress(), formDto.getAddressDetail(),
                formDto.getZoneCode(), member.getTelno(), formDto.getUserNm(), true);
        member.getAddressList().add(address);

        memberRepository.save(member);
        return member;
    }

    public Member findMemberById(Long userId) {
        return memberRepository.findById(userId).orElseThrow(() -> new MemberListException("존재하지 않는 회원입니다."));
    }

    public Member findMemberByEmail(String userEmail) {
        return memberRepository.findMemberByUserEmail(userEmail).orElseThrow(() -> new MemberListException("존재하지 않는 회원입니다."));
    }

    public boolean isMemberByUserEmail(String userEmail) {
        return memberRepository.findMemberByUserEmail(userEmail).isPresent();
    }


    private Member memberBuild(RegisterForm formDto) {
        return Member.builder()
                .userEmail(formDto.getUserEmail())
                .pwd(passwordEncoder.encode(formDto.getPwd()))
                .userNm(formDto.getUserNm())
                .telno(formDto.getTelno())
                .rcmdrEmail(formDto.getRcmdrEmail())
                .gender(formDto.getGender())
                .prvcArge(formDto.isPrvcArge())
                .build();
    }

    private void checkPwd(Member member, String pwd) {
        if (!member.getPwd().equals(pwd)) {
            throw new LoginFormCheckException("아이디 또는 비밀번호가 틀립니다.");
        }
    }
}

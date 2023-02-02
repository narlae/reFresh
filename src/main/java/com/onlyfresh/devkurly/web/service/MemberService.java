package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.Address;
import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.domain.member.MemberAuthoritiesCode;
import com.onlyfresh.devkurly.domain.member.MemberAuthoritiesMapping;
import com.onlyfresh.devkurly.repository.MemberAuthoritiesCodeRepository;
import com.onlyfresh.devkurly.repository.MemberAuthoritiesMappingRepository;
import com.onlyfresh.devkurly.repository.MemberRepository;
import com.onlyfresh.devkurly.web.dto.jwt.TokenInfo;
import com.onlyfresh.devkurly.web.dto.member.LoginFormDto;
import com.onlyfresh.devkurly.web.dto.member.MemberMainResponseDto;
import com.onlyfresh.devkurly.web.dto.member.RegisterForm;
import com.onlyfresh.devkurly.web.exception.*;
import com.onlyfresh.devkurly.web.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberAuthoritiesMappingRepository mappingRepository;
    private final MemberAuthoritiesCodeRepository codeRepository;

    @Transactional
    public TokenInfo login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
        log.info("=======================================authenticationToken={}", authenticationToken);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("=======================================authentication={}", authentication);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        log.info("=======================================tokenInfo={}", tokenInfo);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return tokenInfo;
    }

    public Member findMemberByEmail(String userEmail) {
        return memberRepository.findMemberByUserEmail(userEmail)
                .orElseThrow(() -> new NotFoundDBException("찾는 유저가 없습니다."));
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

    public boolean isMemberByUserEmail(String userEmail) {
        return memberRepository.findMemberByUserEmail(userEmail).isPresent();
    }

    public MemberMainResponseDto extractDto(HttpSession session) {
        return (MemberMainResponseDto) Optional.ofNullable(session.getAttribute("loginMember"))
                .orElseThrow(() -> new SignInException("로그인이 필요합니다."));

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

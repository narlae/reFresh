package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.domain.member.MemberAuthoritiesMapping;
import com.onlyfresh.devkurly.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return memberRepository.findMemberByUserEmail(userEmail)
                .map(m->new User(m.getUserEmail(), m.getPwd(), memberService.getAuthorities(m)))
                .orElseThrow(() -> new AuthenticationServiceException("존재하지 않는 사용자입니다."));
    }


}
package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.domain.member.MemberAuthoritiesMapping;
import com.onlyfresh.devkurly.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return memberRepository.findMemberByUserEmail(userEmail)
                .map(m->new User(m.getUserEmail(), m.getPwd(), getAuthorities(m)))
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Member member) {
        String[] userRoles =  convert(member.getMemberAuthoritiesMappingList());
        return AuthorityUtils.createAuthorityList(userRoles);
    }
    public String[] convert(List<MemberAuthoritiesMapping> list) {
        return list.stream()
                .map(mapping -> mapping.getMemberAuthoritiesCode().getAuthority()).toArray(String[]::new);
    }

}

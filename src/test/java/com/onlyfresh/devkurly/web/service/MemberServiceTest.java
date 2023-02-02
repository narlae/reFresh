package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.domain.member.MemberAuthoritiesMapping;
import com.onlyfresh.devkurly.repository.MemberAuthoritiesMappingRepository;
import com.onlyfresh.devkurly.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberAuthoritiesMappingRepository mappingRepository;
    @Autowired
    MemberService memberService;

    @Test
    public void test() {
        Member member = memberService.findMemberByEmail("aaa@aaa.net");
        List<MemberAuthoritiesMapping> mappings = mappingRepository.findAllByMember(member);
        MemberAuthoritiesMapping m1 = mappings.get(0);
        MemberAuthoritiesMapping m2 = member.getMemberAuthoritiesMappingList().get(0);
        assertEquals(m1,m2);
    }

}
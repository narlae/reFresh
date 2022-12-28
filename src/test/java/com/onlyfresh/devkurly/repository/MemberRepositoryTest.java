package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.member.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void CreateMemberTest() {
        Member member = Member.builder()
                .user_email("aaa@aa.net")
                .user_nm("kim")
                .pwd("asdf")
                .build();

        Member member1 = memberRepository.save(member);

        assertEquals(member, member1);

        memberRepository.delete(member);
        long count = memberRepository.count();
        assertEquals(0, count);


    }

}
package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.Address;
import com.onlyfresh.devkurly.domain.member.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void CreateMemberTest() {
        Member member = Member.builder()
                .userEmail("aaaa@aa.net")
                .userNm("kim")
                .pwd("asdf")
                .build();

        Member member1 = memberRepository.save(member);

        assertEquals(member, member1);

        Optional<Member> byUserEmail = memberRepository.findMemberByUserEmailAndPwd("aaaa@aa.net");
        Member member2 = byUserEmail.get();
        System.out.println("member2 = " + member2);

//        memberRepository.delete(member);
//        long count = memberRepository.count();
//        assertEquals(0, count);
    }

    @Test
    public void isMembertest() {
        boolean present = memberRepository.findMemberByUserEmail("kyjttaa13@naver.com").isPresent();
        System.out.println(present);
    }

    @Test
    public void test3() {
        Member member = memberRepository.findById(4001L).get();
        List<Address> addressList = member.getAddressList();
        Member member1 = null;
        for (Address address : addressList) {
            member1 = address.getMember();
        }
        assertEquals(member, member1);
    }


}
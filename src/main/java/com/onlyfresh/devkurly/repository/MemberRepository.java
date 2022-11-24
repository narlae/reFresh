package com.onlyfresh.devkurly.repository;


import com.onlyfresh.devkurly.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}

package com.onlyfresh.devkurly.repository;


import com.onlyfresh.devkurly.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.user_email = :user_email and m.pwd = :pwd")
    Optional<Member> findMemberByUser_emailAndPwd(String user_email, String pwd);

}

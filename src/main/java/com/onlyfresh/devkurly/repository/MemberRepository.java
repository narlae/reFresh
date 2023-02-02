package com.onlyfresh.devkurly.repository;


import com.onlyfresh.devkurly.domain.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.userEmail = :userEmail")
    Optional<Member> findMemberByUserEmailAndPwd(String userEmail);

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths =
            {"memberAuthoritiesMappingList","memberAuthoritiesMappingList.memberAuthoritiesCode"})
    Optional<Member> findMemberByUserEmail(String userEmail);
}

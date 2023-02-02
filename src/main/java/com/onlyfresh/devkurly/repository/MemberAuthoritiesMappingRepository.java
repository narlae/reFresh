package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.domain.member.MemberAuthoritiesMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAuthoritiesMappingRepository extends JpaRepository<MemberAuthoritiesMapping, Long> {

    List<MemberAuthoritiesMapping> findAllByMember(Member member);
}

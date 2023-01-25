package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.Address;
import com.onlyfresh.devkurly.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findAddressByMemberAndDefaultAdd(Member member, boolean defaultAdd);
}

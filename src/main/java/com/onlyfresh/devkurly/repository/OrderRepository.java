package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.domain.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
//    Orders findOrdersByMember(Member member);
}

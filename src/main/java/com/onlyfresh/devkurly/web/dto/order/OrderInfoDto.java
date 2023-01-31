package com.onlyfresh.devkurly.web.dto.order;

import com.onlyfresh.devkurly.domain.order.Orders;
import lombok.Data;

import java.time.LocalDate;
@Data
public class OrderInfoDto {
    private Long orderId;
    private LocalDate approved_at; //결제성공 승인 날짜
    private LocalDate availability_date; //배송 예정 날짜
    private Integer total_amount;

    public OrderInfoDto(Orders order) {
        this.orderId = order.getOrderId();
        this.approved_at = LocalDate.from(order.getApproved_at());
        this.total_amount = order.getTotal_amount();
        this.availability_date = LocalDate.from(order.getApproved_at()).plusDays(1);
        //임시, 후에 배송관련 클래스로 만들 예정
    }
}

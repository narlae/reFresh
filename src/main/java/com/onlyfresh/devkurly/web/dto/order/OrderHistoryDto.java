package com.onlyfresh.devkurly.web.dto.order;

import com.onlyfresh.devkurly.domain.order.Orders;
import lombok.Data;

@Data
public class OrderHistoryDto {
    private Long orderId;
    private String item_name;
    private Integer quantity;
    private Integer total_amount;

    private String statusCd; //주문대기 주문완료 주문취소 주문오류
    private boolean deliYn; //배송완료 여부

    public OrderHistoryDto(Orders order) {
        this.orderId = order.getOrderId();
        this.item_name = order.getItem_name();
        this.quantity = order.getQuantity();
        this.total_amount = order.getTotal_amount();
        this.statusCd = order.getStatusCd();
        this.deliYn = order.isDeliYn();
    }
}

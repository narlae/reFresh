package com.onlyfresh.devkurly.web.dto.order;

import com.onlyfresh.devkurly.web.dto.CartForm;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderFormDto {

    private String item_name;
    private Integer quantity;
    private Integer total_amount;

    private Long orderId;
    public OrderFormDto(Long orderId, String item_name, Integer quantity, Integer total_amount) {
        this.orderId = orderId;
        this.item_name = item_name;
        this.quantity = quantity;
        this.total_amount = total_amount;
    }
}

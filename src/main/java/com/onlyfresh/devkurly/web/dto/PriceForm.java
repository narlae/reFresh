package com.onlyfresh.devkurly.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class PriceForm {
    private Integer totalSelPrice;

    public PriceForm(List<CartForm> cartFormList) {
        this.totalSelPrice = cartFormList.stream().mapToInt(m -> m.getSelPrice() * m.getQuantity()).sum();
    }
}

package com.onlyfresh.devkurly.web.dto;

import lombok.Data;

@Data
public class CartForm {

    private Long pdtId;
    private String image;
    private String title;
    private Integer quantity;
    private Integer price;
    private Integer dsRate;
    private Integer selPrice;
    private Integer stock;

    public CartForm(Long pdtId, Integer quantity) {
        this.pdtId = pdtId;
        this.quantity = quantity;
    }
}

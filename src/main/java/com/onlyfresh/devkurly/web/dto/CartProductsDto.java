package com.onlyfresh.devkurly.web.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CartProductsDto {
    private Map<Long, Integer> products;

    public CartProductsDto() {
        this.products = new HashMap<>();
    }
}

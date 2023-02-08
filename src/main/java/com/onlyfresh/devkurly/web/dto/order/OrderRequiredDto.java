package com.onlyfresh.devkurly.web.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequiredDto {

    private String item_name;
    private Integer total_amount;
    private Integer total_quantity;
    private List<PdtQutDto> list;

    public OrderRequiredDto(String item_name, Integer total_amount, Integer total_quantity, List<PdtQutDto> list) {
        this.item_name = item_name;
        this.total_amount = total_amount;
        this.total_quantity = total_quantity;
        this.list = list;
    }
}

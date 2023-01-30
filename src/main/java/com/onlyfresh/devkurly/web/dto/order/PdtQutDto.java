package com.onlyfresh.devkurly.web.dto.order;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
public class PdtQutDto {
    private Long pdtId;
    private Integer quantity;

    public PdtQutDto(Long pdtId, Integer quantity) {
        this.pdtId = pdtId;
        this.quantity = quantity;
    }
}

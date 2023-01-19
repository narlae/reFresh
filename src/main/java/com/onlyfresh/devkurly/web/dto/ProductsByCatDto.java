package com.onlyfresh.devkurly.web.dto;

import com.onlyfresh.devkurly.domain.product.Product;
import lombok.Data;

@Data
public class ProductsByCatDto {
    private Long pdtId;
    private String image;
    private boolean deType;
    private String title;
    private Integer dsRate;
    private Integer selPrice;
    private Integer price;
    private String tagName;

    private Integer salesRate;

    public ProductsByCatDto(Product product) {
        this.pdtId = product.getPdtId();
        this.image = product.getImage();
        this.deType = product.isDeType();
        this.title = product.getTitle();
        this.dsRate = product.getDsRate();
        this.selPrice = product.getSelPrice();
        this.price = product.getPrice();
        this.tagName = product.getTagName();
        this.salesRate = product.getSalesRate();
    }
}

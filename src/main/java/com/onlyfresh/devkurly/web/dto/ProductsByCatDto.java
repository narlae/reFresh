package com.onlyfresh.devkurly.web.dto;

import com.onlyfresh.devkurly.domain.product.Product;
import lombok.Data;

import java.util.Comparator;
import java.util.Date;

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

    private Date inDate;


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
        this.inDate = product.getInDate();
    }

    public int compareTo(Object o, String sort_option) {
        ProductsByCatDto m = (ProductsByCatDto) o;
        if (sort_option.equals("dsRate")) {
            return m.dsRate - this.dsRate;
        }
        else if (sort_option.equals("salesRate")) {
            return m.salesRate - this.salesRate;
        } else {
            return -this.inDate.compareTo(m.inDate);
        }
    }
}

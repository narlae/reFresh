package com.onlyfresh.devkurly.web.dto;

import com.onlyfresh.devkurly.domain.product.Product;
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

    public CartForm() {
    }

    public CartForm(Long pdtId, Integer quantity) {
        this.pdtId = pdtId;
        this.quantity = quantity;
    }

    public static CartForm getCartForm(Product product, Integer quantity) {
        CartForm cartForm = new CartForm();
        cartForm.setPdtId(product.getPdtId());
        cartForm.setImage(product.getImage());
        cartForm.setTitle(product.getTitle());
        cartForm.setQuantity(quantity);
        cartForm.setSelPrice(product.getSelPrice());
        return cartForm;
    }
}

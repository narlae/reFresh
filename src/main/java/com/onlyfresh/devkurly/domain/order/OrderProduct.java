package com.onlyfresh.devkurly.domain.order;

import com.onlyfresh.devkurly.domain.Address;
import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.domain.product.Product;
import com.onlyfresh.devkurly.web.dto.CartForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderProduct {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pdtId")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Orders order;

    private Integer quantity;

    public OrderProduct(Product product, Orders order, Integer quantity) {

        this.product = product;
        this.order = order;
        this.quantity = quantity;
    }

    public OrderProduct() {

    }
}

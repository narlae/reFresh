package com.onlyfresh.devkurly.domain;

import com.onlyfresh.devkurly.domain.product.Product;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CategoryProduct {

    @Id @GeneratedValue
    @Column(name = "category_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catId")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pdtId")
    private Product product;

}

package com.onlyfresh.devkurly.domain;

import com.onlyfresh.devkurly.domain.product.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
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

    public static CategoryProduct createCategoryProduct(Product product) {
        CategoryProduct categoryProduct = new CategoryProduct();
        categoryProduct.setProduct(product);
        product.setCategoryProducts(categoryProduct);
        return categoryProduct;
    }

}

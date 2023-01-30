package com.onlyfresh.devkurly.domain.product;

import com.onlyfresh.devkurly.domain.CategoryProduct;
import com.onlyfresh.devkurly.domain.order.OrderProduct;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue
    private Long pdtId;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private ProductDetail productDetail;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CategoryProduct> categoryProducts = new ArrayList<>();

    @NotNull
    private String title; //제목
    private String subTitle; //소제목

    @NotNull
    private Integer price; //가격
    private String image;

    @Builder.Default
    private Integer dsRate = 0; //할인율
    private Integer selPrice; //판매가격


    private String recInfo; //입고안내
    private boolean adtSts; // 성인인증여부
    private Integer stock; //재고

    private Integer salesRate; //판매량
    private boolean deType; //배송유형
    private String tagName; //태그명
    private String company; //제조사

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date inDate;
    private String inUser;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date upDate;
    private String upUser;

    public void setCategoryProducts(CategoryProduct categoryProduct) {
        this.categoryProducts.add(categoryProduct);
    }

    public void setPdtD(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }
    public Product() {

    }
}

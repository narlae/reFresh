package com.onlyfresh.devkurly.domain.product;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class ProductDetail {

    @Id
    @GeneratedValue
    @Column(name = "pdtDId")
    private Long pdtDId;

    @OneToOne(mappedBy = "productDetail", fetch = FetchType.LAZY)
    private Product product;

    private String packCd; //포장타입(상온, 냉장,냉동)
    private String sellCd; //판매단위
    private String wecaCd; //중량/용량
    private String allgDt; //알레르기 정보
    private String exDt; //유통기한
    private String origin; //원산지
    private String notice; //안내사항
    private String prtInfo; //상품설명
    private String prtImage; //상품 고화질 이미지
    private String company; //제조사
    private boolean deType; //배송유형 (t:샛별, f:낮배송)

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date inDate;
    private String inUser;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date upDate;
    private String upUser;

}

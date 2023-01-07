package com.onlyfresh.devkurly.domain.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter @Setter
public class Product {
    @Id
    @GeneratedValue
    private Long pdtId;

    private String catCode; //카테고리 코드

    private Integer price; //가격
    private String image;

    private Integer dsRate; //할인율
    private Integer selPrice; //판매가격

    private String title; //제목
    private String subTitle; //소제목
    private String recInfo; //입고안내
    private boolean adtSts; // 성인인증여부
    private Integer stock; //재고



    private Integer salesRate; //판매량
    private boolean deType; //배송유형
    private String tagName; //태그명
    private String company; //제조사

    private String cdName;
    private String cdTypeName;
    private Integer cdNameNum;

    private Date inDate;
    private String inUser;
    private Date upDate;
    private String upUser;
}

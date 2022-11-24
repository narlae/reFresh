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
    private Integer pdt_id;

    private String cat_code; //카테고리 코드

    private Integer price; //가격
    private String image;

    private Integer ds_rate; //할인율
    private Integer sel_price; //판매가격

    private String title; //제목
    private String sub_title; //소제목
    private String rec_info; //입고안내
    private boolean adt_sts; // 성인인증여부
    private Integer stock; //재고



    private Integer sales_rate; //판매량
    private boolean de_type; //배송유형
    private String tag_name; //태그명
    private String company; //제조사

    private String cd_name;
    private String cd_type_name;
    private Integer cd_name_num;

    private Date in_date;
    private String in_user;
    private Date up_date;
    private String up_user;
}

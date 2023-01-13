package com.onlyfresh.devkurly.web.dto;

import com.onlyfresh.devkurly.domain.product.ProductDetail;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailDto {
    private Long pdtId;
    private String title; //제목
    private String subTitle; //소제목
    private String image;
    private boolean deType; //배송유형 (t:샛별, f:낮배송)
    private Integer dsRate = 0; //할인율
    private Integer selPrice; //판매가격
    private Integer price;

    private String packCd; //포장타입(상온, 냉장,냉동)
    private String sellCd; //판매단위
    private String wecaCd; //중량/용량
    private String allgDt; //알레르기 정보
    private String exDt; //유통기한
    private String origin; //원산지
    private String notice; //안내사항
    private String prtImage; //상품 고화질 이미지
    private String prtInfo; //상품설명

    public void setDetailVariable(ProductDetail productDetail) {
        this.packCd = productDetail.getPackCd();
        this.sellCd = productDetail.getSellCd();
        this.wecaCd = productDetail.getWecaCd();
        this.allgDt = productDetail.getAllgDt();
        this.exDt = productDetail.getExDt();
        this.origin = productDetail.getOrigin();
        this.notice = productDetail.getNotice();
        this.prtImage = productDetail.getPrtImage();
        this.prtInfo = productDetail.getPrtInfo();
    }
}

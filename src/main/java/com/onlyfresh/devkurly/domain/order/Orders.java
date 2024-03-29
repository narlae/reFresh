package com.onlyfresh.devkurly.domain.order;

import com.onlyfresh.devkurly.domain.Address;
import com.onlyfresh.devkurly.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Orders {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addId")
    private Address address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList = new ArrayList<>();

    //주문의 이름
    private String item_name;
    private Integer quantity;
    private Integer total_amount;

    @NotNull
    private String statusCd; //결제대기 결제완료 결제오류
    private String reqPlcCd; //요청 수령 장소 코드
    private String reqDtls; //세부 요청사항
    private Integer usedAcamt; //사용된 포인트
    private boolean deliYn; //배송완료 여부
    private LocalDateTime approved_at; //결제성공 승인 날짜


    public Orders(Member member, Address address) {
        this.member = member;
        this.address = address;
    }

    public Orders() {

    }
}

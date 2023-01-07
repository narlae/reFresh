package com.onlyfresh.devkurly.domain.board;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.domain.product.Product;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Getter @Setter @ToString
public abstract class Board {

    @Id
    @GeneratedValue
    private Long bbsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pdtId")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member member;

    @Column(length = 60)
    private String bbsTitle;

    @Temporal(TemporalType.DATE)
    private Date wrtDt;

    @Lob
    private String bbsCn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date inDate;
    private String inUser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date upDate;
    private String upUser;

}

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
    private Integer bbs_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pdt_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @Column(length = 60)
    private String bbs_title;

    @Temporal(TemporalType.DATE)
    private Date wrt_dt;

    @Lob
    private String bbs_cn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date in_date;
    private String in_user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date up_date;
    private String up_user;

}

package com.onlyfresh.devkurly.domain.board;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.domain.product.Product;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @CreationTimestamp
    private LocalDateTime wrtDt;

    @Lob
    private String bbsCn;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date inDate;
    private String inUser;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date upDate;
    private String upUser;

}

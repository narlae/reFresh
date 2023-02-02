package com.onlyfresh.devkurly.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class MemberAuthoritiesMapping {
    @Id
    @GeneratedValue
    private long memberAuthoritiesMappingSeq;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberAuthoritiesCodeSeq")
    private MemberAuthoritiesCode memberAuthoritiesCode;

    @Column
    @CreationTimestamp
    private LocalDate registerDate;

    @Builder
    public MemberAuthoritiesMapping(Member member, MemberAuthoritiesCode memberAuthoritiesCode) {
        this.member = member;
        this.memberAuthoritiesCode = memberAuthoritiesCode;
    }

    public MemberAuthoritiesMapping() {

    }
}

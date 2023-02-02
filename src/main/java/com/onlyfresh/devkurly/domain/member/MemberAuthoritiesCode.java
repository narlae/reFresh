package com.onlyfresh.devkurly.domain.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@ToString
public class MemberAuthoritiesCode {

    @Id
    @GeneratedValue
    private long memberAuthoritiesCodeSeq;

    @Column(nullable = false)
    private String authority;

    @Column
    @CreationTimestamp
    private LocalDateTime registerDate;

}

package com.onlyfresh.devkurly.domain;

import com.onlyfresh.devkurly.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue
    private Long addId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member member;
    @NotEmpty
    private String address;
    private String addressDetail;
    @NotEmpty
    private String zoneCode;

    private String telno;
    @NotEmpty
    private String recipient;

    private boolean defaultAdd;



    protected Address() {
    }

    public Address(Member member, String address, String addressDetail, String zoneCode, String telno, String recipient, boolean defaultAdd) {
        this.member = member;
        this.address = address;
        this.addressDetail = addressDetail;
        this.zoneCode = zoneCode;
        this.telno = telno;
        this.recipient = recipient;
        this.defaultAdd = defaultAdd;
    }

    @Override
    public String toString() {
        return this.address;
    }

    public static Address of(Member member, String mainAddress, String addressDetail,
                             String zoneCode, String telno, String recipient, boolean defaultAdd) {
        return new Address(member, mainAddress, addressDetail, zoneCode, telno, recipient, defaultAdd);
    }
}

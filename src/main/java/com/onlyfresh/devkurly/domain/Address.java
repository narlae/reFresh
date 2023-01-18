package com.onlyfresh.devkurly.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
public class Address {
    private String address;
    private String addressDetail;
    private String zoneCode;

    protected Address() {
    }
}

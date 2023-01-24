package com.onlyfresh.devkurly.web.dto;

import com.onlyfresh.devkurly.domain.Address;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddressForm {

    private Long addId;
    @NotEmpty(message = "주소 입력은 필수입니다. 주소를 검색해주세요.")
    private String zoneCode;
    @NotEmpty(message = "주소 입력은 필수입니다. 주소를 검색해주세요.")
    private String address;
    @NotEmpty(message = "세부 주소도 입력해주세요.")
    private String addressDetail;

    @NotEmpty(message = "받으실분은 필수입니다.")
    private String recipient;

    @NotEmpty(message = "전화번호는 필수입니다.")
    private String telno;

    private boolean defaultAdd;

    public AddressForm createForm(Address address) {
        this.addId = address.getAddId();
        this.zoneCode = address.getZoneCode();
        this.address = address.getAddress();
        this.addressDetail = address.getAddressDetail();
        this.recipient = address.getRecipient();
        this.telno = address.getTelno();
        this.defaultAdd = address.isDefaultAdd();
        return this;
    }
}

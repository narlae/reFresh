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

    @NotEmpty
    private String recipient;


    protected Address() {
    }

    public Address(Member member, String address, String addressDetail, String zoneCode, String recipient) {
        this.member = member;
        this.address = address;
        this.addressDetail = addressDetail;
        this.zoneCode = zoneCode;
        this.recipient = recipient;
    }

    public static Address of(Member member, String mainAddress, String addressDetail,
                             String zoneCode, String recipient, boolean isDeafultAdd) {
        Address address = new Address(member, mainAddress, addressDetail, zoneCode, recipient);
        List<Address> addressList = member.getAddressList();
        addAddressByDefaultValue(isDeafultAdd, address, addressList);
        return address;
    }

    private static void addAddressByDefaultValue(boolean isDeafultAdd, Address address, List<Address> addressList) {
        if (!isDeafultAdd) {
            addressList.add(address);
        }else{
            if (!addressList.isEmpty()) {
                addressList.add(address);
                Collections.swap(addressList, 0, addressList.size()-1);
            }
            addressList.add(address);
        }
    }
}

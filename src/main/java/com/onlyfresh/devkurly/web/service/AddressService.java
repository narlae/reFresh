package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.Address;
import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.repository.AddressRepository;
import com.onlyfresh.devkurly.web.dto.AddressForm;
import com.onlyfresh.devkurly.web.exception.AddressLimitException;
import com.onlyfresh.devkurly.web.exception.MemberListException;
import com.onlyfresh.devkurly.web.exception.NotFoundDBException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberService memberService;

    @Transactional
    public void saveAddress(Long userId, AddressForm addressForm) {
        Member member = memberService.findMemberById(userId);
        if (member.getAddressList().size() > 4) {
            throw new AddressLimitException("주소지 등록은 5개까지만 가능합니다.");
        }
        Address address = Address.of(member, addressForm.getAddress(), addressForm.getAddressDetail(),
                addressForm.getZoneCode(), addressForm.getTelno(), addressForm.getRecipient(), addressForm.isDefaultAdd());

        ifIsDefault(addressForm, member, address);

        addressRepository.save(address);
    }
    public void deleteAddress(Long userId, Long addId) {
        Member member = memberService.findMemberById(userId);
        Address address = addressRepository.findById(addId).orElseThrow(() -> new NotFoundDBException("찾는 address가 없습니다."));
        if (!address.getMember().equals(member)) {
            throw new MemberListException("유저와 주소가 일치하지 않습니다.");
        }
        addressRepository.delete(address);
    }

    public List<AddressForm> getUserAddressList(Long userId) {
        Member member = memberService.findMemberById(userId);
        List<Address> addressList = member.getAddressList();
        List<AddressForm> list = new ArrayList<>();
        for (Address address : addressList) {
            list.add(new AddressForm().createForm(address));
        }
        return list;
    }

    public AddressForm getDefault(Long userId) {
        Member member = memberService.findMemberById(userId);
        Address address = addressRepository.findAddressByMemberAndDefaultAdd(member, true);
        return dtoFromAddress(address);
    }

    private void ifIsDefault(AddressForm addressForm, Member member, Address address) {
        List<Address> addressList = member.getAddressList();

        if (!addressList.isEmpty()) {
            if (addressForm.isDefaultAdd()) {
                for (Address item : addressList) {
                    item.setDefaultAdd(false);
                }
            }
        }
        addressList.add(address);
    }

    private AddressForm dtoFromAddress(Address address) {
        AddressForm addressForm = new AddressForm();
        addressForm.setAddress(address.getAddress());
        addressForm.setAddressDetail(address.getAddressDetail());
        return addressForm;
    }
}

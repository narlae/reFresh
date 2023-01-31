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

    @Transactional
    public void updateAddress(Long userId, AddressForm addressForm) {
        Member member = memberService.findMemberById(userId);
        Address address = addressRepository.findById(addressForm.getAddId()).orElseThrow(() -> new NotFoundDBException("해당하는 주소지가 없습니다."));
        if (!address.getMember().equals(member)) {
            throw new MemberListException("유저와 주소가 일치하지 않습니다.");
        }

        if (addressForm.isDefaultAdd()) {
            List<Address> addressList = member.getAddressList();
            for (Address item : addressList) {
                item.setDefaultAdd(false);
            }
        }
        addressFormToAddress(addressForm, address);
    }

    public void deleteAddress(Long userId, Long addId) {
        Member member = memberService.findMemberById(userId);
        Address address = addressRepository.findById(addId).orElseThrow(() -> new NotFoundDBException("해당하는 주소지가 없습니다."));
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

    public AddressForm editPage(Long userId, Long addId) {
        Member member = memberService.findMemberById(userId);
        Address address = addressRepository.findById(addId).orElseThrow(() -> new NotFoundDBException("해당하는 주소지가 없습니다."));
        if (!member.getAddressList().contains(address)) {
            throw new MemberListException("유저와 주소가 일치하지 않습니다.");
        }
        AddressForm addressForm = new AddressForm();
        return addressForm.createForm(address);
    }

    public AddressForm getDefault(Long userId) {
        Member member = memberService.findMemberById(userId);
        Address address = addressRepository.findAddressByMemberAndDefaultAdd(member, true);
        return dtoFromAddress(address);
    }

    public AddressForm getAllDefault(Long userId) {
        Member member = memberService.findMemberById(userId);
        Address address = addressRepository.findAddressByMemberAndDefaultAdd(member, true);
        return dtoFromAllAddress(address);
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

    public AddressForm dtoFromAllAddress(Address address) {
        AddressForm addressForm = new AddressForm();
        addressForm.setAddId(address.getAddId());
        addressForm.setAddress(address.getAddress());
        addressForm.setAddressDetail(address.getAddressDetail());
        addressForm.setRecipient(address.getRecipient());
        addressForm.setTelno(address.getTelno());
        addressForm.setZoneCode(address.getZoneCode());
        return addressForm;
    }

    private void addressFormToAddress(AddressForm addressForm, Address address) {
        address.setAddress(addressForm.getAddress());
        address.setAddressDetail(addressForm.getAddressDetail());
        address.setDefaultAdd(addressForm.isDefaultAdd());
        address.setRecipient(addressForm.getRecipient());
        address.setTelno(addressForm.getTelno());
        address.setZoneCode(addressForm.getZoneCode());
    }
}

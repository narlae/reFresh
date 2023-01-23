package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.Address;
import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.repository.AddressRepository;
import com.onlyfresh.devkurly.web.dto.AddressForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberService memberService;

    @Transactional
    public void saveAddress(Long userId, AddressForm addressForm) {
        Member member = memberService.findMemberById(userId);
        Address address = Address.of(member, addressForm.getAddress(), addressForm.getAddressDetail(),
                addressForm.getZoneCode(), addressForm.getRecipient(), addressForm.isDefaultAdd());

        addressRepository.save(address);
    }
}

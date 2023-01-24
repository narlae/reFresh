package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.web.dto.AddressForm;
import com.onlyfresh.devkurly.web.exception.SignInException;
import com.onlyfresh.devkurly.web.service.AddressService;
import com.onlyfresh.devkurly.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String getAddressPage() {
        return "myPage/address/address";
    }

    @GetMapping("/add")
    public String getAddressAddPage(AddressForm addressForm) {
        return "myPage/address/addressForm";
    }

    @PostMapping("/addressList")
    @ResponseBody
    public List<AddressForm> getAddressList(@RequestBody Long userId) {
        return addressService.getUserAddressList(userId);
    }

    @PostMapping("/add")
    public String registerAdd(HttpSession session, AddressForm addressForm) {
        Long userId = getUserId(session);
        addressService.saveAddress(userId, addressForm);
        return "redirect:/address/list";
    }

    @DeleteMapping("/{index}")
    public String deleteAdd(HttpSession session, @PathVariable Long index) {
        Long userId = getUserId(session);
        addressService.deleteAddress(userId, index);
        return "myPage/address/address";
    }

    private Long getUserId(HttpSession session) {
        return Optional.ofNullable(memberService.extractDto(session).getUserId())
                .orElseThrow(() -> new SignInException("로그인이 필요합니다."));
    }
}

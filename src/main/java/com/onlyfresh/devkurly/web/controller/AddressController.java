package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.web.dto.AddressForm;
import com.onlyfresh.devkurly.web.exception.SignInException;
import com.onlyfresh.devkurly.web.service.AddressService;
import com.onlyfresh.devkurly.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
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

    @PostMapping("/add")
    public String registerAdd(HttpSession session, AddressForm addressForm) {
        Long userId = getUserId(session);
        addressService.saveAddress(userId, addressForm);
        return "myPage/address/address";
    }

    private Long getUserId(HttpSession session) {
        return Optional.ofNullable(memberService.extractDto(session).getUserId())
                .orElseThrow(() -> new SignInException("로그인이 필요합니다."));
    }
}

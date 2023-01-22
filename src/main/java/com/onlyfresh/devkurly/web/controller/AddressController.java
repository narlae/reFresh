package com.onlyfresh.devkurly.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/address")
public class AddressController {

    @GetMapping("/list")
    public String getAddressPage() {
        return "myPage/address/address";
    }

    @GetMapping("/add")
    public String getAddressAddPage() {
        return "myPage/address/addressForm";
    }
}

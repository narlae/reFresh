package com.onlyfresh.devkurly.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/pdtDetail")
    public String getPdtDForm() {
        return "/products/admin_product_detail";
    }
}

package com.onlyfresh.devkurly.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping("/cart")
    public String getCartPage() {

        return "order/cart";
    }
}

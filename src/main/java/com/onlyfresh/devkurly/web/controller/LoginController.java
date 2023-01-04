package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.web.dto.LoginFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginController {

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginFormDto form, String redirectURL, HttpServletRequest request) {

        return "/";
    }
}

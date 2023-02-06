package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.web.dto.member.LoginFormDto;
import com.onlyfresh.devkurly.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/loginForm")
    public String loginForm(LoginFormDto loginFormDto, HttpServletRequest request, String error, String loginFailMsg, Model model) {
        String URL = request.getHeader("Referer");
        if (request.getSession() != null) {
            if (URL != null && !URL.contains("/login")) {
                request.getSession(false).setAttribute("prevPage", URL);
            }
        }

        model.addAttribute("error", error);
        model.addAttribute("loginFailMsg", loginFailMsg);
        return "members/login";
    }
}

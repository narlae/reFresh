package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.web.dto.jwt.TokenInfo;
import com.onlyfresh.devkurly.web.dto.member.MemberMainResponseDto;
import com.onlyfresh.devkurly.web.dto.member.RegisterForm;
import com.onlyfresh.devkurly.web.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/register")
    public String registerForm(RegisterForm registerForm) {
        return "members/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterForm registerForm, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "members/register";
        }

        Member member = memberService.registerMember(registerForm);
        TokenInfo tokenInfo = memberService.login(member.getUserEmail(), member.getPwd(), true);
        addCookie(response, tokenInfo);
        return "members/regComplete";
    }

    @PostMapping("/register/checkDupli")
    @ResponseBody
    public boolean checkEmail(@RequestBody String userEmail) {
        return memberService.isMemberByUserEmail(userEmail);
    }

    private void addCookie(HttpServletResponse response, TokenInfo tokenInfo) {
        ResponseCookie accessCookie = ResponseCookie.from("tokenInfo", tokenInfo.getAccessToken())
                .httpOnly(true)
                .maxAge(60 * 60)
                .path("/")
                .build();
        response.addHeader("Set-Cookie",accessCookie.toString());

        if (tokenInfo.getRefreshToken()!=null) {
            ResponseCookie refreshCookie = ResponseCookie.from("refreshTokenInfo", tokenInfo.getRefreshToken())
                    .httpOnly(true)
                    .maxAge(60 * 60 * 24 * 7)
                    .path("/")
                    .build();
            response.addHeader("Set-Cookie",refreshCookie.toString());
        }
    }
}



package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.repository.MemberRepository;
import com.onlyfresh.devkurly.web.dto.member.LoginFormDto;
import com.onlyfresh.devkurly.web.dto.member.MemberMainResponseDto;
import com.onlyfresh.devkurly.web.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public LoginController(MemberRepository memberRepository, MemberService memberService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String loginForm(HttpSession session, LoginFormDto loginFormDto) {
        if (session.getAttribute("loginMember") == null) {
            return "members/login";
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginFormDto loginFormDto, BindingResult bindingResult, String toURL, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "members/login";
        }
        MemberMainResponseDto memberMainResponseDto = memberService.checkMember(loginFormDto);

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", memberMainResponseDto);


        return "redirect:" + toURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}

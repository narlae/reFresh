package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.repository.MemberRepository;
import com.onlyfresh.devkurly.web.dto.LoginFormDto;
import com.onlyfresh.devkurly.web.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class LoginController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public LoginController(MemberRepository memberRepository, MemberService memberService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String loginForm(HttpSession session, Model model) {
        model.addAttribute("loginForm", new LoginFormDto());
        if (session.getAttribute("memberResponse") == null) {
            return "members/login";
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginFormDto form, String toURL, HttpServletRequest request) {
        memberService.checkMember(form.getUser_email(), form.getPwd());

        return "redirect:" + toURL;
    }
}

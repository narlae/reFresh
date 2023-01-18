package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.web.dto.member.MemberMainResponseDto;
import com.onlyfresh.devkurly.web.dto.member.RegisterForm;
import com.onlyfresh.devkurly.web.service.MemberService;
import lombok.extern.slf4j.Slf4j;
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
    public String register(@Valid @ModelAttribute RegisterForm registerForm, BindingResult bindingResult, HttpServletRequest request, Model model) {
        if (bindingResult.hasErrors()) {
            return "members/register";
        }

        MemberMainResponseDto memberMainResponseDto = memberService.registerMember(registerForm);
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", memberMainResponseDto);

        //회원가입 완료, 축하 창 만들어서 넣기
        return "redirect:/";
    }
}

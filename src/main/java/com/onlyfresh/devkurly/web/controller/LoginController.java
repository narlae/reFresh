package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.repository.MemberRepository;
import com.onlyfresh.devkurly.web.dto.member.LoginFormDto;
import com.onlyfresh.devkurly.web.dto.member.MemberMainResponseDto;
import com.onlyfresh.devkurly.web.exception.ErrorCode;
import com.onlyfresh.devkurly.web.exception.LoginFormCheckException;
import com.onlyfresh.devkurly.web.service.MemberService;
import com.onlyfresh.devkurly.web.utils.SessionConst;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

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
        if (session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            return "members/login";
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginFormDto loginFormDto, BindingResult bindingResult, String toURL, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {// fieldError
            return "members/login";
        }
        MemberMainResponseDto memberMainResponseDto = null;
        try {
            memberMainResponseDto = memberService.checkMember(loginFormDto);
        } catch (LoginFormCheckException e) {
            bindingResult.reject(ErrorCode.INVALID_LOGIN.getCode(), e.getMessage());// globalError
            return "members/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, memberMainResponseDto);


        return "redirect:" + toURL;
    }

    @PostMapping("/test/login1")
    @ResponseBody
    public String loginTest(@Valid LoginFormDto loginFormDto, BindingResult bindingResult) throws NoSuchMethodException, MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(this.getClass().getDeclaredMethod("loginTest", LoginFormDto.class, BindingResult.class), 0), bindingResult);
        }
        return "success";
    }

    @PostMapping("/test/login2")
    @ResponseBody
    public String loginTest2(@RequestBody @Valid LoginFormDto loginFormDto){
        return "success";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionConst.LOGIN_MEMBER) != null) {
            session.removeAttribute(SessionConst.LOGIN_MEMBER);
        }
        return "redirect:/";
    }
}

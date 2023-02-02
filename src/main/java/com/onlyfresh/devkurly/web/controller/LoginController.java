package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.repository.MemberRepository;
import com.onlyfresh.devkurly.web.dto.jwt.TokenInfo;
import com.onlyfresh.devkurly.web.dto.member.LoginFormDto;
import com.onlyfresh.devkurly.web.dto.member.MemberMainResponseDto;
import com.onlyfresh.devkurly.web.exception.ErrorCode;
import com.onlyfresh.devkurly.web.exception.LoginFormCheckException;
import com.onlyfresh.devkurly.web.service.MemberService;
import com.onlyfresh.devkurly.web.utils.SecurityUtil;
import com.onlyfresh.devkurly.web.utils.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
public class LoginController {

    private final MemberService memberService;

    public LoginController(MemberRepository memberRepository, MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/loginForm")
    public String loginForm(LoginFormDto loginFormDto) {
        return "members/login";
    }

    @PostMapping("/login")
    public TokenInfo login(@Valid @ModelAttribute LoginFormDto loginFormDto, BindingResult bindingResult, HttpServletResponse response) {
        log.info("=======================================loginFormDto={}", loginFormDto);
        if (bindingResult.hasErrors()) {// fieldError
            return "members/login";
        }
        String userEmail = loginFormDto.getUserEmail();
        String pwd = loginFormDto.getPwd();
        TokenInfo tokenInfo = memberService.login(userEmail, pwd);
        log.info("========================================Member={}", SecurityUtil.getCurrentMemberId());
        ResponseCookie responseCookie = ResponseCookie.from("tokenInfo", tokenInfo.getRefreshToken())
                .httpOnly(true)
                .maxAge(60 * 60)
                .path("/")
                .build();
        return tokenInfo;
    }

    @PostMapping("/login2")
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

}

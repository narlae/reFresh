package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.web.dto.jwt.TokenInfo;
import com.onlyfresh.devkurly.web.dto.member.LoginFormDto;
import com.onlyfresh.devkurly.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/loginForm")
    public String loginForm(LoginFormDto loginFormDto, HttpServletRequest request) {
        String URL = request.getHeader("Referer");
        if (URL != null && !URL.contains("/login")) {
            request.getSession().setAttribute("prevPage", URL);
        }
        System.out.println("request.getSession() = " + request.getSession().getAttribute("prevPage"));
        return "members/login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @ModelAttribute LoginFormDto loginFormDto, BindingResult bindingResult,
                                HttpServletResponse response, HttpServletRequest request) {
//        if (bindingResult.hasErrors()) {// fieldError
//            return "members/login";
//        }
        String userEmail = loginFormDto.getUserEmail();
        String pwd = loginFormDto.getPwd();
        if (!memberService.isAccessTokenAndValidate(request)) { //access가 없으면

            if (memberService.isRefreshTokenAndValidate(request)) { //refresh가 있다면 (aceess가 만료됨)

                TokenInfo tokenInfo = memberService.login(userEmail, pwd, false);//access만 재발급
                addCookie(response, tokenInfo);
                log.info("====================access만 재발급===========");
            }else{
                TokenInfo tokenInfo = memberService.login(userEmail, pwd, true);//모두 재발급
                addCookie(response, tokenInfo);
                log.info("====================모두 재발급===========");
            }
        }else { //access가 있으면 검증
            memberService.getAuthentication(userEmail, pwd);
            log.info("====================토큰 발급X 검증O===========");
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
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

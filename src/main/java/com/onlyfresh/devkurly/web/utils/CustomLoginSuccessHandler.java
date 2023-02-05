package com.onlyfresh.devkurly.web.utils;

import com.onlyfresh.devkurly.web.dto.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("========================================successhandler도달");

        if (!isAccessTokenAndValidate(request)) { //access가 없으면

            if (isRefreshTokenAndValidate(request)) { //refresh가 있다면 (aceess가 만료됨)
                TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, false);
                addCookie(response, tokenInfo);
                log.info("====================access만 재발급===========");
            }else{
                TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, true);
                addCookie(response, tokenInfo);
                log.info("====================모두 재발급===========");
            }
        }

        clearSession(request);

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        String prevPage = (String) request.getSession().getAttribute("prevPage");
        if (prevPage != null) {
            request.getSession().removeAttribute("prevPage");
        }

        // 기본 URI
        String uri = "/";

        /**
         * savedRequest 존재하는 경우 = 인증 권한이 없는 페이지 접근
         * Security Filter가 인터셉트하여 savedRequest에 세션 저장
         */
        if (savedRequest != null) {
            uri = savedRequest.getRedirectUrl();
        } else if (prevPage != null && !prevPage.equals("")) {
            // 회원가입 - 로그인으로 넘어온 경우 "/"로 redirect
            if (prevPage.contains("/register")) {
                uri = "/";
            } else {
                uri = prevPage;
            }
        }
        log.info("============================SavedRequest========{}", savedRequest);

        redirectStrategy.sendRedirect(request, response, uri);


        //



//        HttpSession session = request.getSession();
//        if (session != null) {
//            String redirectUrl = (String) session.getAttribute("prevPage");
//            if (redirectUrl != null) {
//                session.removeAttribute("prevPage");
//                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
//            } else {
//                super.onAuthenticationSuccess(request, response, authentication);
//            }
//        } else {
//            super.onAuthenticationSuccess(request, response, authentication);
//        }
    }


    private boolean isAccessTokenAndValidate(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> optional = Arrays.stream(cookies).filter(c -> c.getName().equals("tokenInfo")).findFirst();
        return optional.filter(cookie -> jwtTokenProvider.validateToken(cookie.getValue())).isPresent();
    }

    private boolean isRefreshTokenAndValidate(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> optional = Arrays.stream(cookies).filter(c -> c.getName().equals("refreshTokenInfo")).findFirst();
        return optional.filter(cookie -> jwtTokenProvider.validateToken(cookie.getValue())).isPresent();
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

    // 로그인 실패 후 성공 시 남아있는 에러 세션 제거
    protected void clearSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}

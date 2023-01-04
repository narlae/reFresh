package com.onlyfresh.devkurly.web.interceptor;

import com.onlyfresh.devkurly.web.SessionConst;
import com.onlyfresh.devkurly.web.exception.SignInException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional.ofNullable(request.getSession().getAttribute(SessionConst.LOGIN_MEMBER))
                .orElseThrow(() ->
                        new SignInException("로그인이 필요합니다."));
        return true;
    }
}

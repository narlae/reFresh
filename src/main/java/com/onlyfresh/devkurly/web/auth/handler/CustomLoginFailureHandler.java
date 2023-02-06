package com.onlyfresh.devkurly.web.auth.handler;

import com.onlyfresh.devkurly.web.dto.member.LoginFormDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("========================================failure handler도달");
        log.info("=========================exception=============={}", exception);

        String loginFailMsg = "";
        if (exception instanceof AuthenticationServiceException) {
            loginFailMsg = "존재하지 않는 사용자입니다.";
        } else if(exception instanceof BadCredentialsException) {
            loginFailMsg = "아이디 또는 비밀번호가 틀립니다.";
        } else if(exception instanceof LockedException) {
            loginFailMsg = "잠긴 계정입니다.";
        } else if(exception instanceof DisabledException) {
            loginFailMsg = "비활성화된 계정입니다.";
        } else if(exception instanceof AccountExpiredException) {
            loginFailMsg = "만료된 계정입니다.";
        } else if(exception instanceof CredentialsExpiredException) {
            loginFailMsg = "비밀번호가 만료되었습니다";
        }
        loginFailMsg = URLEncoder.encode(loginFailMsg, StandardCharsets.UTF_8);
//        String userEmail = request.getParameter("userEmail");
//        String pwd = request.getParameter("pwd");
//
//        LoginFormDto loginFormDto = new LoginFormDto();
//        loginFormDto.setUserEmail(userEmail);
//        loginFormDto.setPwd(pwd);
//        request.setAttribute("loginFormDto", loginFormDto);
//        log.info("===================================loginFormDto===={}", request.getAttribute("loginFormDto"));
//
//        request.getRequestDispatcher("/login/test").forward(request, response);
        response.sendRedirect("/loginForm?error=true&loginFailMsg="+loginFailMsg);

    }
}

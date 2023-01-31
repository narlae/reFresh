package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.order.Orders;
import com.onlyfresh.devkurly.web.dto.order.KakaoPayApprovalVO;
import com.onlyfresh.devkurly.web.dto.order.OrderFormDto;
import com.onlyfresh.devkurly.web.exception.OrderErrorException;
import com.onlyfresh.devkurly.web.exception.SignInException;
import com.onlyfresh.devkurly.web.service.KakaoPay;
import com.onlyfresh.devkurly.web.service.MemberService;
import com.onlyfresh.devkurly.web.service.OrderService;
import com.onlyfresh.devkurly.web.utils.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final OrderService orderService;
    private final KakaoPay kakaoPay;
    private final MemberService memberService;


    @PostMapping("/kakaoPay")
    public String kakaoPay(@RequestParam Map<String, String> paramMap, HttpSession session) {

        Long userId = getUserId(session);
        Orders order = orderService.createOrder(userId, paramMap);
        session.setAttribute(SessionConst.ORDER_IMFO,
                new OrderFormDto(order.getOrderId(), order.getItem_name(),order.getQuantity(), order.getTotal_amount()));
        return "redirect:" + kakaoPay.kakaoPayReady(order);
    }

    @GetMapping("/kakaoPaySuccess")
    public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model, HttpSession session) {
        Long userId = getUserId(session);
        OrderFormDto orderFormDto = getOrderDtoFromSession(session);
        KakaoPayApprovalVO kakaoPayApprovalVO = Optional.ofNullable(kakaoPay.kakaoPayInfo(pg_token, orderFormDto, userId))
                .orElseThrow(() -> new OrderErrorException("결제가 실패했습니다."));

        Orders order = orderService.orderSuccessLogic(orderFormDto);

        model.addAttribute("info", kakaoPayApprovalVO);

        return "redirect:/order/list";
    }

    private Long getUserId(HttpSession session) {
        return Optional.ofNullable(memberService.extractDto(session).getUserId())
                .orElseThrow(() -> new SignInException("로그인이 필요합니다."));
    }





    private OrderFormDto getOrderDtoFromSession(HttpSession session) {
        return (OrderFormDto) Optional.ofNullable(session.getAttribute(SessionConst.ORDER_IMFO)).
                orElseThrow(() -> new OrderErrorException("잘못된 접근입니다."));
    }
}

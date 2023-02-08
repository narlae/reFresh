package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.order.Orders;
import com.onlyfresh.devkurly.web.dto.order.KakaoPayApprovalVO;
import com.onlyfresh.devkurly.web.dto.order.OrderFormDto;
import com.onlyfresh.devkurly.web.dto.order.OrderRequiredDto;
import com.onlyfresh.devkurly.web.dto.order.PdtQutDto;
import com.onlyfresh.devkurly.web.exception.OrderErrorException;
import com.onlyfresh.devkurly.web.service.KakaoPay;
import com.onlyfresh.devkurly.web.service.MemberService;
import com.onlyfresh.devkurly.web.service.OrderService;
import com.onlyfresh.devkurly.web.utils.SecurityUtil;
import com.onlyfresh.devkurly.web.utils.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
        String userEmail = SecurityUtil.getCurrentMemberId();
        List<PdtQutDto> list = orderService.getPdtQutDtos(paramMap);
        OrderRequiredDto orderRequiredDto = orderService.getOrderRequiredDto(list);
        session.setAttribute(SessionConst.ORDER_INFO, orderRequiredDto);
        String orderId = String.valueOf(UUID.randomUUID());
        return "redirect:" + kakaoPay.kakaoPayReady(orderId, userEmail, orderRequiredDto);
    }

    @GetMapping("/kakaoPaySuccess")
    public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, String orderId, Integer total_amount, Model model, HttpSession session) {
        String userEmail = SecurityUtil.getCurrentMemberId();
        log.info("====================================orderId==================={}", orderId);
        KakaoPayApprovalVO kakaoPayApprovalVO = Optional.ofNullable(kakaoPay.kakaoPayInfo(pg_token, orderId, total_amount, userEmail))
                .orElseThrow(() -> new OrderErrorException("결제가 실패했습니다."));

        OrderRequiredDto orderRequiredDto = getOrderRequiredDto(session);
        Orders order = orderService.createOrder(orderId, userEmail, orderRequiredDto); //주문내역

        //주문내역, 카트상품 세션 지우기
        session.removeAttribute(SessionConst.ORDER_INFO);
        session.removeAttribute(SessionConst.CART_PDT);

        return "redirect:/order/list";
    }

    private OrderRequiredDto getOrderRequiredDto(HttpSession session) {
        return Optional.ofNullable((OrderRequiredDto) session.getAttribute(SessionConst.ORDER_INFO))
                .orElseThrow(() -> new OrderErrorException("잘못된 접근입니다."));
    }

    @GetMapping("/kakaoPayCancel")
    public String kakaoPayCancel() {
        return "redirect:/cart?error=2";
    }


    @GetMapping("/kakaoPaySuccessFail")
    public String kakaoPayFail() {
        return "redirect:/cart?error=3";
    }
}

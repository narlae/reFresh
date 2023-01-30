package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.domain.order.Orders;
import com.onlyfresh.devkurly.web.dto.AddressForm;
import com.onlyfresh.devkurly.web.dto.CartForm;
import com.onlyfresh.devkurly.web.dto.PriceForm;
import com.onlyfresh.devkurly.web.dto.member.OrderMemberForm;
import com.onlyfresh.devkurly.web.dto.order.KakaoPayApprovalVO;
import com.onlyfresh.devkurly.web.dto.order.OrderFormDto;
import com.onlyfresh.devkurly.web.exception.OrderErrorException;
import com.onlyfresh.devkurly.web.exception.SignInException;
import com.onlyfresh.devkurly.web.service.AddressService;
import com.onlyfresh.devkurly.web.service.KakaoPay;
import com.onlyfresh.devkurly.web.service.MemberService;
import com.onlyfresh.devkurly.web.service.OrderService;
import com.onlyfresh.devkurly.web.utils.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final MemberService memberService;
    private final AddressService addressService;
    private final OrderService orderService;

    private final KakaoPay kakaoPay;


    @PostMapping("/orderForm")
    public String orderForm(@RequestParam Map<String, String> paramMap, HttpSession session, Model model) {
        List<CartForm> cartFormList = orderService.getCartFormList(paramMap);
        Long userId = getUserId(session);
        Member member = memberService.findMemberById(userId);
        OrderMemberForm memberForm = new OrderMemberForm(member);
        AddressForm addressForm = addressService.getAllDefault(userId);
        PriceForm priceForm = new PriceForm(cartFormList);
        model.addAttribute("cartFormList", cartFormList);
        model.addAttribute("memberForm", memberForm);
        model.addAttribute("addressForm", addressForm);
        model.addAttribute("priceForm", priceForm);

        return "order/orderForm";
    }
    @PostMapping("/kakaoPay")
    public String kakaoPay(@RequestParam Map<String, String> paramMap, HttpSession session) {

        Long userId = getUserId(session);
        Orders order = orderService.createOrder(userId, paramMap);
        session.setAttribute(SessionConst.ORDER_IMFO,
                new OrderFormDto(order.getOrderId(), order.getItem_name(),order.getQuantity(), order.getTotal_amount()));
        log.info("kakaoPay post............................................");
        return "redirect:" + kakaoPay.kakaoPayReady(order);
    }

    @GetMapping("/kakaoPaySuccess")
    public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model, HttpSession session) {
        Long userId = getUserId(session);
        OrderFormDto orderFormDto = getOrderDtoFromSession(session);
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);
        KakaoPayApprovalVO kakaoPayApprovalVO = Optional.ofNullable(kakaoPay.kakaoPayInfo(pg_token, orderFormDto, userId))
                .orElseThrow(() -> new OrderErrorException("결제가 실패했습니다."));

        Orders order = orderSuccessLogic(kakaoPayApprovalVO, orderFormDto);


        model.addAttribute("info", kakaoPayApprovalVO);

        return "order/orderCompl";
    }

    private Orders orderSuccessLogic(KakaoPayApprovalVO kakaoPayApprovalVO, OrderFormDto orderFormDto) {
        Orders order = orderService.findOrdersByOrderId(orderFormDto.getOrderId());
        order.setStatusCd("주문완료");
        return order;
    }



    private OrderFormDto getOrderDtoFromSession(HttpSession session) {
        return (OrderFormDto) Optional.ofNullable(session.getAttribute(SessionConst.ORDER_IMFO)).
                orElseThrow(() -> new OrderErrorException("잘못된 접근입니다."));
    }


    private Long getUserId(HttpSession session) {
        return Optional.ofNullable(memberService.extractDto(session).getUserId())
                .orElseThrow(() -> new SignInException("로그인이 필요합니다."));
    }
}

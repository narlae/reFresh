package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.web.dto.AddressForm;
import com.onlyfresh.devkurly.web.dto.CartForm;
import com.onlyfresh.devkurly.web.dto.PriceForm;
import com.onlyfresh.devkurly.web.dto.member.OrderMemberForm;
import com.onlyfresh.devkurly.web.exception.SignInException;
import com.onlyfresh.devkurly.web.service.AddressService;
import com.onlyfresh.devkurly.web.service.MemberService;
import com.onlyfresh.devkurly.web.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final MemberService memberService;
    private final AddressService addressService;
    private final OrderService orderService;


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


    private Long getUserId(HttpSession session) {
        return Optional.ofNullable(memberService.extractDto(session).getUserId())
                .orElseThrow(() -> new SignInException("로그인이 필요합니다."));
    }
}

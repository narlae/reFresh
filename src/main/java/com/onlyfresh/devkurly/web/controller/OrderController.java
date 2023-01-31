package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.domain.order.OrderProduct;
import com.onlyfresh.devkurly.domain.order.Orders;
import com.onlyfresh.devkurly.repository.OrderRepository;
import com.onlyfresh.devkurly.web.dto.AddressForm;
import com.onlyfresh.devkurly.web.dto.CartForm;
import com.onlyfresh.devkurly.web.dto.PriceForm;
import com.onlyfresh.devkurly.web.dto.member.OrderMemberForm;
import com.onlyfresh.devkurly.web.dto.order.OrderHistoryDto;
import com.onlyfresh.devkurly.web.dto.order.OrderInfoDto;
import com.onlyfresh.devkurly.web.exception.SignInException;
import com.onlyfresh.devkurly.web.service.AddressService;
import com.onlyfresh.devkurly.web.service.MemberService;
import com.onlyfresh.devkurly.web.service.OrderService;
import com.onlyfresh.devkurly.web.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final MemberService memberService;
    private final AddressService addressService;
    private final OrderService orderService;
    private final ProductService productService;

    private final OrderRepository orderRepository;



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

    @GetMapping("/list")
    public String orderList(HttpSession session, Model model) {
        Long userId = getUserId(session);
        List<OrderHistoryDto> orderDtoList = getOrderDtoList(userId);
        model.addAttribute("orderDtoList", orderDtoList);
        log.info("orderDtoList======================{}", orderDtoList);
        return "myPage/order/orderList";
    }

    @GetMapping("/{orderId}")
    public String getOrderProductPage(@PathVariable Long orderId, HttpSession session, Model model) {
        Long userId = getUserId(session);
        Orders order = orderService.findOrdersByOrderId(orderId);
        if (!orderService.checkOrderNUser(order, userId))
            return "redirect:/order/list?error=1";

        extractedModel(model, order);
        return "myPage/order/orderInfo";
    }

    private void extractedModel(Model model, Orders order) {
        OrderInfoDto orderInfoDto = new OrderInfoDto(order);

        List<CartForm> cartFormList = new ArrayList<>();
        List<OrderProduct> orderProductList = order.getOrderProductList();
        orderProductList.forEach(op -> cartFormList.add(CartForm.getCartForm(op.getProduct(), op.getQuantity())));

        OrderMemberForm memberForm = new OrderMemberForm(order.getMember());
        AddressForm addressForm = addressService.dtoFromAllAddress(order.getAddress());

        model.addAttribute("orderInfoDto", orderInfoDto);
        model.addAttribute("cartFormList", cartFormList);
        model.addAttribute("memberForm", memberForm);
        model.addAttribute("addressForm", addressForm);
    }

    private List<OrderHistoryDto> getOrderDtoList(Long userId) {
        Member member = memberService.findMemberById(userId);
        return orderRepository.findAllByMember(member)
                .stream().map(OrderHistoryDto::new).collect(Collectors.toList());
    }


    private Long getUserId(HttpSession session) {
        return Optional.ofNullable(memberService.extractDto(session).getUserId())
                .orElseThrow(() -> new SignInException("로그인이 필요합니다."));
    }
}

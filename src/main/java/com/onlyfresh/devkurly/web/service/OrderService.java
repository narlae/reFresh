package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.Address;
import com.onlyfresh.devkurly.domain.member.Member;
import com.onlyfresh.devkurly.domain.order.OrderProduct;
import com.onlyfresh.devkurly.domain.order.Orders;
import com.onlyfresh.devkurly.domain.product.Product;
import com.onlyfresh.devkurly.repository.AddressRepository;
import com.onlyfresh.devkurly.repository.OrderRepository;
import com.onlyfresh.devkurly.web.dto.CartForm;
import com.onlyfresh.devkurly.web.dto.order.OrderRequiredDto;
import com.onlyfresh.devkurly.web.dto.order.PdtQutDto;
import com.onlyfresh.devkurly.web.exception.NotFoundDBException;
import com.onlyfresh.devkurly.web.exception.WrongAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;
    private final MemberService memberService;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;

    public List<CartForm> getCartFormList(Map<String, String> map) {
        Map<Long, Integer> collect = getLongIntegerMap(map);
        List<CartForm> list = new ArrayList<>();
        collect.forEach((k, v) -> setValueIntoList(list, k, v));
        return list;
    }

    @Transactional
    public Orders createOrder(String orderId, String userEmail, OrderRequiredDto orderRequiredDto) {
        Member member = memberService.findMemberByEmail(userEmail);
        Address address = addressRepository.findAddressByMemberAndDefaultAdd(member, true);
        Orders order = new Orders(member, address);

        /*
          할인 추가시 수정 필요
         */

        for (PdtQutDto pdtQutDto : orderRequiredDto.getList()) {
            Product product = productService.findProductById(pdtQutDto.getPdtId());
            OrderProduct orderProduct = new OrderProduct(product, order, pdtQutDto.getQuantity());
            order.getOrderProductList().add(orderProduct);
        }
        order.setOrderId(orderId);
        order.setItem_name(orderRequiredDto.getItem_name());
        order.setTotal_amount(orderRequiredDto.getTotal_amount());
        order.setQuantity(orderRequiredDto.getTotal_quantity());
        order.setStatusCd("결제완료");
        order.setDeliYn(false);
        order.setApproved_at(LocalDateTime.now());
        orderRepository.save(order);
        return order;
    }

    public List<PdtQutDto> getPdtQutDtos(Map<String, String> map) {
        Map<Long, Integer> collect = getLongIntegerMap(map);
        List<PdtQutDto> list = new ArrayList<>();
        collect.forEach((k, v) -> setValueDto(list, k, v));

        return list;
    }

    public OrderRequiredDto getOrderRequiredDto(List<PdtQutDto> list) {
        StringBuilder title = new StringBuilder();

        int total_amount = 0;
        int total_quantity = 0;

        for (PdtQutDto pdtQutDto : list) {
            Product product = productService.findProductById(pdtQutDto.getPdtId());
            title.append(product.getTitle());
            title.append(", "); //개선 필요
            total_amount += pdtQutDto.getQuantity() * product.getSelPrice();
            total_quantity += pdtQutDto.getQuantity();
        }
         return new OrderRequiredDto(title.toString(), total_amount, total_quantity, list);
    }


    public Orders findOrdersByOrderId(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new NotFoundDBException("찾을 수 없는 주문입니다."));
    }

    private Map<Long, Integer> getLongIntegerMap(Map<String, String> map) {
        return map.entrySet().stream().collect(Collectors.toMap(
                (entry) -> Long.parseLong(entry.getKey()), (entry) -> Integer.parseInt(entry.getValue())
        ));
    }

    private void setValueIntoList(List<CartForm> list, Long pdtId, Integer quantity) {
        CartForm cartForm = new CartForm(pdtId, quantity);
        Product product = productService.findProductById(pdtId);
        productService.setFieldsByProduct(cartForm, product);
        list.add(cartForm);
    }

    private void setValueDto(List<PdtQutDto> list, Long pdtId, Integer quantity) {
        PdtQutDto pdtQutDto = new PdtQutDto(pdtId, quantity);
        list.add(pdtQutDto);
    }

    public boolean checkOrderNUser(Orders order, String userEmail) {
        Member member = memberService.findMemberByEmail(userEmail);
        if (!order.getMember().equals(member)) {
            throw new WrongAccessException("잘못된 접근입니다.");
        }
        return order.getMember().equals(member);
    }
}

package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.product.Product;
import com.onlyfresh.devkurly.web.dto.CartForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;

    public List<CartForm> getCartFormList(Map<String, String> map) {
        Map<Long, Integer> collect = getLongIntegerMap(map);
        List<CartForm> list = new ArrayList<>();
        collect.forEach((k, v) -> setValueIntoList(list, k, v));
        return list;
    }

    private Map<Long, Integer> getLongIntegerMap(Map<String, String> map) {
        Map<Long, Integer> collect = map.entrySet().stream().collect(Collectors.toMap(
                (entry) -> Long.parseLong(entry.getKey()), (entry) -> Integer.parseInt(entry.getValue())
        ));
        return collect;
    }

    private void setValueIntoList(List<CartForm> list, Long pdtId, Integer quantity) {
        CartForm cartForm = new CartForm(pdtId, quantity);
        Product product = productService.findProductById(pdtId);
        productService.setFieldsByProduct(cartForm, product);
        list.add(cartForm);
    }

}

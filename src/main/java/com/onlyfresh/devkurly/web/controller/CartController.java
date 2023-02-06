package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.product.Product;
import com.onlyfresh.devkurly.repository.ProductRepository;
import com.onlyfresh.devkurly.web.dto.CartForm;
import com.onlyfresh.devkurly.web.dto.CartProductsDto;
import com.onlyfresh.devkurly.web.service.MemberService;
import com.onlyfresh.devkurly.web.service.ProductService;
import com.onlyfresh.devkurly.web.utils.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final ProductService productService;
    @GetMapping("/cart")
    public String getCartPage() {

        return "order/cart";
    }

    @PostMapping("/cart/add")
    @ResponseBody
    public ResponseEntity<String> addCart(@RequestBody CartForm cartForm, HttpServletRequest request) {
        Long pdtId = cartForm.getPdtId();
        Integer quantity = cartForm.getQuantity();
        HttpSession session = request.getSession();
        if (Optional.ofNullable(session.getAttribute(SessionConst.CART_PDT)).isPresent()) {
            CartProductsDto cartProductsDto = (CartProductsDto) session.getAttribute(SessionConst.CART_PDT);
            Map<Long, Integer> products = cartProductsDto.getProducts();
            if (products.containsKey(pdtId)) {
                Integer preValue = products.get(pdtId);
                products.put(pdtId, preValue + quantity);
            }else{
                products.put(pdtId, quantity);
            }
        } else{
            session.setAttribute(SessionConst.CART_PDT, createCartPdtDto(pdtId, quantity));
        }
        return new ResponseEntity<>("Cart_OK", HttpStatus.OK);
    }

    @GetMapping("/cart/list")
    @ResponseBody
    public List<CartForm> getCartList(HttpSession session) {
        log.info("===========================getCartList======================================");
        List<CartForm> list = new ArrayList<>();
        if (Optional.ofNullable(session.getAttribute(SessionConst.CART_PDT)).isPresent()) {
            CartProductsDto dto = (CartProductsDto) session.getAttribute(SessionConst.CART_PDT);
            Map<Long, Integer> products = dto.getProducts();
            products.forEach((key, value) -> list.add(new CartForm(key, value)));
            list.forEach(m->productService.setFieldsByProduct(m, productService.findProductById(m.getPdtId())));
        }
        return list;
    }

    @DeleteMapping("/cart/{pdtId}")
    public ResponseEntity<String> deletePdtInCart(@PathVariable Long pdtId, HttpSession httpSession) {
        CartProductsDto dto = (CartProductsDto) httpSession.getAttribute(SessionConst.CART_PDT);
        dto.getProducts().remove(pdtId);
        httpSession.setAttribute(SessionConst.CART_PDT, dto);
        return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
    }

    @PutMapping("/cart/{pdtId}")
    public ResponseEntity<String> updatePdtInCart(@PathVariable Long pdtId, @RequestBody Integer quantity, HttpSession httpSession) {
        CartProductsDto dto = (CartProductsDto) httpSession.getAttribute(SessionConst.CART_PDT);
        dto.getProducts().put(pdtId, quantity);
        httpSession.setAttribute(SessionConst.CART_PDT, dto);
        return new ResponseEntity<>("PUT_OK", HttpStatus.OK);
    }

    private CartProductsDto createCartPdtDto(Long pdtId, Integer quantity) {
        CartProductsDto cartProductsDto = new CartProductsDto();
        Map<Long, Integer> products = cartProductsDto.getProducts();
        products.put(pdtId, quantity);
        return cartProductsDto;
    }

}

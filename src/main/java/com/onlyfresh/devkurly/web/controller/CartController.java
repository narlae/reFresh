package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.domain.product.Product;
import com.onlyfresh.devkurly.repository.ProductRepository;
import com.onlyfresh.devkurly.web.dto.CartForm;
import com.onlyfresh.devkurly.web.dto.CartProductsDto;
import com.onlyfresh.devkurly.web.service.MemberService;
import com.onlyfresh.devkurly.web.service.ProductService;
import com.onlyfresh.devkurly.web.utils.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final ProductService productService;
    @GetMapping("/cart")
    public String getCartPage() {

        return "order/cart";
    }

    @GetMapping("/cart/add")
    public ResponseEntity<String> addCart(Long pdtId, Integer quantity, HttpServletRequest request) {
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
    public List<CartForm> getCartList(HttpSession session) {
        List<CartForm> list = new ArrayList<>();
        if (Optional.ofNullable(session.getAttribute(SessionConst.CART_PDT)).isPresent()) {
            CartProductsDto dto = (CartProductsDto) session.getAttribute(SessionConst.CART_PDT);
            Map<Long, Integer> products = dto.getProducts();
            products.forEach((key, value) -> list.add(new CartForm(key, value)));
            list.forEach(m->setFieldsByProduct(m, productService.findProductById(m.getPdtId())));
        }
        return list;
    }

    @DeleteMapping("/cart/{pdtId}")
    public String deletePdtInCart(@PathVariable Long pdtId, HttpSession httpSession) {
        CartProductsDto dto = (CartProductsDto) httpSession.getAttribute(SessionConst.CART_PDT);
        dto.getProducts().remove(pdtId);
        httpSession.setAttribute(SessionConst.CART_PDT, dto);
        return "order/cart";
    }

    @PutMapping("/cart/{pdtId}")
    public String updatePdtInCart(@PathVariable Long pdtId, Integer quantity, HttpSession httpSession) {
        CartProductsDto dto = (CartProductsDto) httpSession.getAttribute(SessionConst.CART_PDT);
        dto.getProducts().put(pdtId, quantity);
        httpSession.setAttribute(SessionConst.CART_PDT, dto);
        return "order/cart";
    }

    private CartProductsDto createCartPdtDto(Long pdtId, Integer quantity) {
        CartProductsDto cartProductsDto = new CartProductsDto();
        Map<Long, Integer> products = cartProductsDto.getProducts();
        products.put(pdtId, quantity);
        return cartProductsDto;
    }

    private void setFieldsByProduct(CartForm cartForm, Product product) {
        cartForm.setImage(product.getImage());
        cartForm.setTitle(product.getTitle());
        cartForm.setPrice(product.getPrice());
        cartForm.setDsRate(product.getDsRate());
        cartForm.setSelPrice(product.getSelPrice());
    }
}

package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.web.dto.ProductDetailDto;
import com.onlyfresh.devkurly.web.service.BoardService;
import com.onlyfresh.devkurly.web.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductService productService;
    private final BoardService boardService;

    @GetMapping("/product/{pdtId}")
    public String getPdtPage(@PathVariable Long pdtId, Model model) {
        ProductDetailDto productDetailDto = productService.getProductDetail(pdtId);
        Integer totalCnt = boardService.countBoardByProduct(pdtId);
        model.addAttribute("dto", productDetailDto);
        model.addAttribute(pdtId);
        model.addAttribute("totalCnt", totalCnt);
        return "products/product_detail";
    }
}

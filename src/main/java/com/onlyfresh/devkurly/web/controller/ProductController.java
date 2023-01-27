package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.web.dto.CategoryDto;
import com.onlyfresh.devkurly.web.dto.ProductsByCatDto;
import com.onlyfresh.devkurly.web.service.CategoryService;
import com.onlyfresh.devkurly.web.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("/product/categories")
    @ResponseBody
    public List<CategoryDto> getCategories() {
        return categoryService.getCategoryForm();
    }

    @GetMapping("/products/{catId}")
    public String getProducts(@PathVariable Long catId, Model model) {
        Map<String, String> categoryName = categoryService.getCategoryName(catId);
        model.addAttribute("categoryName", categoryName);
        return "products/category_products";
    }

    @GetMapping("/products/{catId}/{page}")
    @ResponseBody
    public Page<ProductsByCatDto> getProductsList(@PathVariable Long catId, @PathVariable int page,
                                                  String sort_option) {
        return productService.getProductsByCategory(catId, sort_option, page, 9);
    }

    @GetMapping("/products/nav")
    public String getNavProductsPage(String navName, Model model) {
        switch (navName) {
            case "dsRate":
                navName = "알뜰쇼핑";
                break;
            case "salesRate":
                navName = "베스트";
                break;
            case "inDate":
                navName = "신상품";
                break;
        }
        model.addAttribute("navName", navName);
        return "main/navProducts";
    }

    @GetMapping("/products/nav/{page}")
    @ResponseBody
    public Page<ProductsByCatDto> getProductsByNav(@PathVariable int page, String navName) {
//        navName : dsRate, salesRate, inDate
        return productService.getProductsByNavName(navName, page, 9);
    }

    @GetMapping("/products/search")
    public String getSearchPage(String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return "main/searchProducts";
    }

    @GetMapping("/products/search/{page}")
    @ResponseBody
    public Page<ProductsByCatDto> getProductsBySearch(@PathVariable int page, String keyword, String sort_option) {
        return productService.getProductsBySearch(keyword, sort_option, page, 9);
    }

}

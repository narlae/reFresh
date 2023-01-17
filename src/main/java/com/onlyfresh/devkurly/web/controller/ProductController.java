package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.web.dto.CategoryDto;
import com.onlyfresh.devkurly.web.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
public class ProductController {

    private final CategoryService categoryService;

    @GetMapping("/product/categories")
    @ResponseBody
    public CategoryDto getCategories() {
        return categoryService.getCategoryForm();
    }
}

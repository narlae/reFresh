package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.Category;
import com.onlyfresh.devkurly.domain.product.Product;
import com.onlyfresh.devkurly.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void addProduct() {

    }



}

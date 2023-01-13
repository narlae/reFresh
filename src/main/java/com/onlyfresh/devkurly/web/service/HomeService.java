package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.Category;
import com.onlyfresh.devkurly.domain.CategoryProduct;
import com.onlyfresh.devkurly.domain.product.Product;
import com.onlyfresh.devkurly.repository.CategoryProductRepository;
import com.onlyfresh.devkurly.repository.CategoryRepository;
import com.onlyfresh.devkurly.web.dto.MainDto;
import com.onlyfresh.devkurly.web.exception.NotFoundDBException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final CategoryRepository categoryRepository;
    private final CategoryProductRepository categoryProductRepository;


    public List<List<MainDto>> getMainInfo(Long product1, Long product2, Long product3) {
        List<List<MainDto>> list = new ArrayList<>();
        list.add(getMainDtoByCatId(product1));
        list.add(getMainDtoByCatId(product2));
        list.add(getMainDtoByCatId(product3));
        return list;
    }

    private List<MainDto> getMainDtoByCatId(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundDBException("존재하지 않는 카테고리입니다."));

        List<CategoryProduct> categoryProducts =
                categoryProductRepository.findCategoryProductsByCategory(category);
        return categoryProducts.stream().
                map(m -> productToMainDto(m.getProduct())).
                limit(4).
                collect(Collectors.toList());
    }

    private MainDto productToMainDto(Product product) {
        return MainDto.builder()
                .pdtId(product.getPdtId())
                .image(product.getImage())
                .title(product.getTitle())
                .dsRate(product.getDsRate())
                .selPrice(product.getSelPrice())
                .price(product.getPrice())
                .build();
    }
}

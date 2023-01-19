package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.Category;
import com.onlyfresh.devkurly.domain.CategoryProduct;
import com.onlyfresh.devkurly.domain.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class CategoryProductRepositoryTest {
    @Autowired
    CategoryProductRepository categoryProductRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    public void test() {
        Optional<Category> byId = categoryRepository.findById(6L);
        if (byId.isPresent()) {
            Category category = byId.get();
            List<CategoryProduct> categoryProducts =
                    categoryProductRepository.findCategoryProductsByCategory(category);
            List<String> list = categoryProducts.stream().
                    map(m -> m.getProduct()).
                    map(p -> p.getImage()).
                    collect(Collectors.toList());
        }
    }

    @Test
    public void test2() {
        List<CategoryProduct> list = categoryProductRepository.findCategoryProductsByCategory_CatId(2L);
        System.out.println("!!!!!!!!!!!!!!!!!!!list = " + list);
        List<Product> collect = list.stream()
                .map(m -> productRepository.findProductByPdtId(m.getProduct().getPdtId()))
                .collect(Collectors.toList());

        System.out.println("collect = " + collect);

    }


}
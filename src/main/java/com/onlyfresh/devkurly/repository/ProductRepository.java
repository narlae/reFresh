package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.CategoryProduct;
import com.onlyfresh.devkurly.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByTitle(String title);

//    List<Product> findProductsByCategoryProducts(List<CategoryProduct> categoryProducts);

}

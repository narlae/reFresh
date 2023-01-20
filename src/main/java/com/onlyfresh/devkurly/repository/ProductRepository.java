package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.CategoryProduct;
import com.onlyfresh.devkurly.domain.product.Product;
import com.onlyfresh.devkurly.web.dto.ProductDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByTitle(String title);

    @Query("select p.pdtId from Product p")
    List<Long> findPdtIds();

    Product findProductByPdtId(Long pdtId);

}

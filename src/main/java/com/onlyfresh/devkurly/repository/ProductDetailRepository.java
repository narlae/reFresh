package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.product.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

}

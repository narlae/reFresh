package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.CategoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long> {
}

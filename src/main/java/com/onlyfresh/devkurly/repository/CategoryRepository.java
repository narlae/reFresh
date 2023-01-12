package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryByCatName(String catName);

}

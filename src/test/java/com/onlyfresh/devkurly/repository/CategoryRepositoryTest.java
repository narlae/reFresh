package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.Category;
import com.onlyfresh.devkurly.web.dto.CategoryDto;
import com.onlyfresh.devkurly.web.service.CategoryService;
import com.onlyfresh.devkurly.web.utils.CsvReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CategoryRepositoryTest {

    @Autowired
    CsvReader csvReader;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryProductRepository categoryProductRepository;
    @Autowired
    CategoryService categoryService;

    @Test
    public void setCategoryFromCsv() {
        List<List<String>> lists = csvReader.readCSV("category");

        for (List<String> list : lists) {
            Category parentCategory = new Category();
            parentCategory.setCatName(list.get(0));
            categoryRepository.save(parentCategory);

            for (int i = 1; i < list.size(); i++) {
                Category child = new Category();
                if (!list.get(i).equals("")) {
                    child.setCatName(list.get(i));
                    parentCategory.addChildCategory(child);
                    categoryRepository.save(child);
                }
            }
        }

        List<Category> all = categoryRepository.findAll();
        System.out.println("all = " + all);
    }

    @Test
    public void addCategoryProduct() {

    }


}
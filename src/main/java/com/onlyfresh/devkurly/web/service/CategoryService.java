package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.Category;
import com.onlyfresh.devkurly.repository.CategoryRepository;
import com.onlyfresh.devkurly.web.dto.CategoryDto;
import com.onlyfresh.devkurly.web.exception.NotFoundDBException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getCategoryForm() {

        List<Category> All = categoryRepository.findAll();
        if (All.isEmpty()) {
            throw new NotFoundDBException("카테고리가 없습니다.");
        }
        return All.stream().filter(m -> m.getParent() == null)
                .map(CategoryDto::new).sorted().collect(Collectors.toList());
    }

    public Map<String, String> getCategoryName(Long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() -> new RuntimeException("해당하는 카테고리가 없습니다."));
        Map<String, String> map = new HashMap<String, String>();
        String catName = category.getCatName();
        if (getCategoryById(catId).getParent() == null) {
            map.put("parent", catName);
            return map;
        }
        String catParentName = category.getParent().getCatName();
        map.put("parent", catParentName);
        map.put("child", catName);
        return map;

    }

    public Category getCategoryById(Long catId) {
        return categoryRepository.findById(catId).orElseThrow(() -> new NotFoundDBException("찾는 카테고리가 없습니다."));
    }
}

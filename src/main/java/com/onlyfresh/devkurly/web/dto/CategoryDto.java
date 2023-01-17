package com.onlyfresh.devkurly.web.dto;

import com.onlyfresh.devkurly.domain.Category;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@ToString
@Getter
public class CategoryDto {

    private final Map<String, List<String>> categoryList = new LinkedHashMap<>();

    public void setCategoryDto(String parentNm , List<Category> child) {
        List<String> childList = new ArrayList<>();
        categoryList.put(parentNm, childList);
        for (Category category : child) {
            childList.add(category.getCatName());
        }
    }

}

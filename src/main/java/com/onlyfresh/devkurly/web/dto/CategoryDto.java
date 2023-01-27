package com.onlyfresh.devkurly.web.dto;

import com.onlyfresh.devkurly.domain.Category;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@ToString
@Getter
public class CategoryDto implements Comparable{

    private final Long catId;
    private final String catName;
    private final List<SubCategoryDto> childName;

    public CategoryDto(Category category) {
        this.catId = category.getCatId();
        this.catName = category.getCatName();
        this.childName = category.getChild().stream().map(SubCategoryDto::new).collect(Collectors.toList());
    }

    @Override
    public int compareTo(Object o) {
        CategoryDto o1 = (CategoryDto) o;
        return (int) (this.catId - o1.getCatId());
    }


}

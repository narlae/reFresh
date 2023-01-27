package com.onlyfresh.devkurly.web.dto;

import com.onlyfresh.devkurly.domain.Category;
import lombok.Data;

@Data
public class SubCategoryDto {
    Long catId;
    String catName;

    public SubCategoryDto(Category category) {
        this.catId = category.getCatId();
        this.catName = category.getCatName();
    }
}

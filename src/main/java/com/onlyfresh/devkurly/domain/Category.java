package com.onlyfresh.devkurly.domain;

import com.onlyfresh.devkurly.domain.product.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "catId")
    private Long catId;

    private String catName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<CategoryProduct> categoryProducts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }

    public void addCategoryProducts(CategoryProduct categoryProduct) {
        categoryProducts.add(categoryProduct);
        categoryProduct.setCategory(this);
    }

    public static Category createCategory(CategoryProduct... categoryProducts) {
        Category category = new Category();
        for (CategoryProduct categoryProduct : categoryProducts) {
            category.addCategoryProducts(categoryProduct);
        }
        return category;
    }

    @Override
    public String toString() {
        return this.catId + "." + this.catName + ": ";
    }
}

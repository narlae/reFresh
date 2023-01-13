package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.Category;
import com.onlyfresh.devkurly.domain.CategoryProduct;
import com.onlyfresh.devkurly.domain.product.Product;
import com.onlyfresh.devkurly.domain.product.ProductDetail;
import com.onlyfresh.devkurly.web.dto.ProductDetailDto;
import com.onlyfresh.devkurly.web.utils.CsvReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ProductRepositoryTest {

    @Autowired
    CsvReader csvReader;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void readCsv(){
        List<List<String>> lists = csvReader.readCSV("product");
        System.out.println("lists = " + lists);
    }

    @Test
    public void setProductFromCsv(){
        List<List<String>> lists = csvReader.readCSV("product");
        lists.remove(0);

        for (List<String> list : lists) {
            Product product = Product.builder()
                    .title(list.get(0))
                    .subTitle(list.get(1))
                    .dsRate(Integer.valueOf(list.get(2)))
                    .selPrice(Integer.valueOf(list.get(3)))
                    .price(Integer.valueOf(list.get(4)))
                    .image(list.get(5))
                    .recInfo(list.get(6))
                    .adtSts(Boolean.parseBoolean(list.get(7)))
                    .stock(Integer.valueOf(list.get(8)))
                    .salesRate(Integer.valueOf(list.get(9)))
                    .deType(Boolean.parseBoolean(list.get(10)))
                    .tagName(list.get(11))
                    .company(list.get(12))
                    .build();

            CategoryProduct categoryProduct = CategoryProduct.createCategoryProduct(product);
            Category category = categoryRepository.findCategoryByCatName(list.get(13));
            category.addCategoryProducts(categoryProduct);
            productRepository.save(product);
        }
    }

    @Test
    public void createProduct() {
        Product product = Product.builder()
                .price(1000)
                .title("사과")
                .subTitle("맛있는 사과")
                .stock(100)
                .deType(true)
                .build();

        CategoryProduct categoryProduct = CategoryProduct.createCategoryProduct(product);
        Category category = Category.createCategory(categoryProduct);
        category.setCatName("과일");
        productRepository.save(product);
        categoryRepository.save(category);


        List<Product> list = productRepository.findProductsByTitle("사과");
        Long pdtId = list.get(0).getPdtId();
        System.out.println("pdtId = " + pdtId);
        Product product1 = productRepository.findById(pdtId).get();
        System.out.println("product1 = " + product1);
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryProduct> categoryProducts = categoryList.get(0).getCategoryProducts();
        Product product2 = categoryProducts.get(0).getProduct();
        System.out.println("product2 = " + product2);

        assertEquals(product1, product2);
    }

    @Test
    public void getPdtIdTest() {
        List<Long> pdtIds = productRepository.findPdtIds();
        System.out.println("pdtIds = " + pdtIds);
    }
}
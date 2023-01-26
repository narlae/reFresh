package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.CategoryProduct;
import com.onlyfresh.devkurly.domain.product.Product;
import com.onlyfresh.devkurly.domain.product.ProductDetail;
import com.onlyfresh.devkurly.repository.CategoryProductRepository;
import com.onlyfresh.devkurly.repository.ProductDetailRepository;
import com.onlyfresh.devkurly.repository.ProductRepository;
import com.onlyfresh.devkurly.web.dto.CartForm;
import com.onlyfresh.devkurly.web.dto.ProductDetailDto;
import com.onlyfresh.devkurly.web.dto.ProductsByCatDto;
import com.onlyfresh.devkurly.web.exception.NotFoundDBException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository detailRepository;
    private final CategoryProductRepository categoryProductRepository;


    public ProductDetailDto getProductDetail(Long pdtId) {
        Product product = productRepository.findById(pdtId).
                orElseThrow(() -> new NotFoundDBException("찾는 상품이 없습니다."));
        ProductDetailDto dto = getDto(product);
        Optional.ofNullable(product.getProductDetail())
                .ifPresent(dto::setDetailVariable);
        return dto;
    }

    @Transactional
    public void insertPrtDetail(ProductDetailDto dto) {
        Product product = productRepository.
                findById(dto.getPdtId()).
                orElseThrow(() -> new NotFoundDBException("찾는 상품이 없습니다."));
        if (Optional.ofNullable(product.getProductDetail()).isPresent()) {
            throw new RuntimeException("이미 상품디테일이 존재합니다.");
        }
        ProductDetail productDetail = getDetail(product, dto);
        detailRepository.save(productDetail);
        product.setPdtD(productDetail);
    }

    @Transactional
    public void updatePrtDetail(ProductDetailDto dto) {
        Product product = productRepository.
                findById(dto.getPdtId()).
                orElseThrow(() -> new NotFoundDBException("찾는 상품이 없습니다."));
        ProductDetail productDetail = Optional.ofNullable(product.getProductDetail())
                .orElseThrow(() -> new RuntimeException("수정할 상품디테일이 존재하지 않습니다."));
        updateDetail(productDetail, dto);
    }

    public Page<ProductsByCatDto> getProductsByCategory(Long catId, String sort_option, int page, int pageSize) {

        PageRequest pageRequest = PageRequest.of(page, pageSize);

        List<ProductsByCatDto> productsByCatId = getProductsByCatId(catId, sort_option);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), productsByCatId.size());


        return new PageImpl<>(productsByCatId.subList(start, end), pageRequest, productsByCatId.size());
    }

    private List<ProductsByCatDto> getProductsByCatId(Long catId, String sort_option) {
        List<CategoryProduct> list = categoryProductRepository.findCategoryProductsByCategory_CatId(catId);
        return list.stream()
                .map(m -> productRepository.findProductByPdtId(m.getProduct().getPdtId()))
                .map(ProductsByCatDto::new)
                .sorted((m1, m2) -> m1.compareTo(m2, sort_option))
                .collect(Collectors.toList());
    }


    public Product findProductById(Long pdtId) {
        return productRepository.findById(pdtId)
                .orElseThrow(() -> new NotFoundDBException("존재하지 않는 상품입니다."));
    }

    private ProductDetailDto getDto(Product product) {
        return ProductDetailDto.builder()
                .pdtId(product.getPdtId())
                .title(product.getTitle())
                .subTitle(product.getSubTitle())
                .image(product.getImage())
                .deType(product.isDeType())
                .dsRate(product.getDsRate())
                .selPrice(product.getSelPrice())
                .price(product.getPrice())
                .build();
    }

    private ProductDetail getDetail(Product product, ProductDetailDto dto) {
        return ProductDetail.builder()
                .product(product)
                .packCd(dto.getPackCd())
                .sellCd(dto.getSellCd())
                .wecaCd(dto.getWecaCd())
                .allgDt(dto.getAllgDt())
                .exDt(dto.getExDt())
                .origin(dto.getOrigin())
                .notice(dto.getNotice())
                .prtInfo(dto.getPrtInfo())
                .prtImage(dto.getPrtImage())
                .company(product.getCompany())
                .deType(dto.isDeType())
                .build();
    }

    private void updateDetail(ProductDetail productDetail, ProductDetailDto dto) {
        productDetail.setSellCd(dto.getSellCd());
        productDetail.setWecaCd(dto.getWecaCd());
        productDetail.setAllgDt(dto.getAllgDt());
        productDetail.setExDt(dto.getExDt());
        productDetail.setOrigin(dto.getOrigin());
        productDetail.setNotice(dto.getNotice());
        productDetail.setPrtInfo(dto.getPrtInfo());
        productDetail.setPrtImage(dto.getPrtImage());
        productDetail.setDeType(dto.isDeType());
    }

    public void setFieldsByProduct(CartForm cartForm, Product product) {
        cartForm.setImage(product.getImage());
        cartForm.setTitle(product.getTitle());
        cartForm.setPrice(product.getPrice());
        cartForm.setDsRate(product.getDsRate());
        cartForm.setSelPrice(product.getSelPrice());
        cartForm.setStock(product.getStock());
    }



}

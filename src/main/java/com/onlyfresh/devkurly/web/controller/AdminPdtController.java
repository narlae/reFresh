package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.repository.ProductRepository;
import com.onlyfresh.devkurly.web.dto.ProductDetailDto;
import com.onlyfresh.devkurly.web.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminPdtController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    @GetMapping("/detail/list")
    public ResponseEntity<List<Long>> getPdtid() {
        try {
            List<Long> pdtIds = productRepository.findPdtIds();
            return new ResponseEntity<>(pdtIds, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/detail/{pdtId}")
    public ResponseEntity<ProductDetailDto> getProduct(@PathVariable Long pdtId) {

        try {
            ProductDetailDto productDetailDto = productService.getProductDetail(pdtId);
            return new ResponseEntity<>(productDetailDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/detail/add")
    @ResponseBody
    public ResponseEntity<String> addDetail(@RequestBody ProductDetailDto productDetailDto) {

        try {
            productService.insertPrtDetail(productDetailDto);
            return new ResponseEntity<>("WRT_DETAIL_OK",HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("WRT_DETAIL_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/detail/update")
    public ResponseEntity<ProductDetailDto> modifyDetail(@RequestBody ProductDetailDto productDetailDto) {
        try {
            productService.updatePrtDetail(productDetailDto);
            return new ResponseEntity<ProductDetailDto>(productDetailDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ProductDetailDto>(HttpStatus.BAD_REQUEST);
        }
    }

}

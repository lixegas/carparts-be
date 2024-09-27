package com.morelli.carparts.controller;

import com.morelli.carparts.model.dto.ProductDTO;

import com.morelli.carparts.model.entity.Product;
import com.morelli.carparts.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/storage/product")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    // GET PRODUCT
    @GetMapping("/")
    public ResponseEntity<ProductDTO> getProduct(@RequestParam Long barCode) {
        return productService.getProductByQrCode(barCode);
    }

    // CREATE PRODUCT
    @PostMapping("/")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }
}

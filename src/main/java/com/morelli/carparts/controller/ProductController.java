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

    // GET PRODUCT (ALERT: IF IT NOT EXIST)
    //IT WORKS
    @GetMapping("/")
    public ResponseEntity<ProductDTO> getProduct(@RequestParam Long barCode) {
        return productService.getProductByQrCode(barCode);
    }

    // CREATE PRODUCT (ALERT: IF ALREADY EXIST, IF IS NOT VALID)
    //IT WORKS
    @PostMapping("/")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    // MODIFY PRODUCT (ALERT: IF PRODUCT WITH THIS BARCODE IS NOT FOUND)
    // TODO: TO CHECK
    @PutMapping("/put")
    public ResponseEntity<Product> updateProduct(@RequestParam Long barCode, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(barCode, productDTO);
    }

    //DELETE PRODUCT (MESSAGE: DELETING SUCCESSFULLY)
    //IT WORKS
    @DeleteMapping("/delete")
    public void deleteProduct(@RequestParam Long barCode) {
        productService.deleteProduct(barCode);
    }


}

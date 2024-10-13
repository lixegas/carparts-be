package com.morelli.carparts.controller;

import com.morelli.carparts.model.dto.ProductDTO;
import com.morelli.carparts.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/storage/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    // GET all products with optional filtering
    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String productSize,
            @RequestParam(required = false) String productName) {

        List<ProductDTO> productDTOs = productService.getAll(page, size, color, productSize, productName);
        return ResponseEntity.ok(productDTOs);
    }


    // GET product by barcode
    @GetMapping("/{barCode}")
    public ResponseEntity<ProductDTO> getProductByBarcode(@PathVariable Long barCode) {
        ProductDTO productDTO = productService.getProductByBarCode(barCode);
        return ResponseEntity.ok(productDTO);
    }


    // POST create product
    @PostMapping("/")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.add(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // PUT update product
    @PutMapping("/{barCode}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long barCode, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.update(barCode, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    // DELETE product
    @DeleteMapping("/{barCode}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long barCode) {
        productService.delete(barCode);
        return ResponseEntity.noContent().build();
    }
}


package com.morelli.carparts.service;

import com.morelli.carparts.mapper.ProductMapper;
import com.morelli.carparts.model.dto.ProductDTO;
import com.morelli.carparts.model.entity.Product;
import com.morelli.carparts.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ResponseEntity<ProductDTO> getProductByQrCode(Long barCode) {
        Product product = productRepository.getProductByBarCode(barCode);

        if (product != null) {
            ProductDTO productDTO = productMapper.toProductDTO(product);
            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<Product> createProduct(ProductDTO productDTO) {
        try {

            Product productAlreadyExist = productRepository.getProductByBarCode(productDTO.getBarCode());

            if (productAlreadyExist != null) {

                throw new IllegalArgumentException("A product with this barcode already exists.");
            }

            Product product = productMapper.toProduct(productDTO);
            productRepository.save(product);

            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.SC_CONFLICT)
                    .body(null);
        } catch (Exception e) {
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}

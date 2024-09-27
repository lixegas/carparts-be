package com.morelli.carparts.service;

import com.morelli.carparts.mapper.ProductMapper;
import com.morelli.carparts.model.dto.ProductDTO;
import com.morelli.carparts.model.entity.Category;
import com.morelli.carparts.model.entity.Product;
import com.morelli.carparts.repository.CategoryRepository;
import com.morelli.carparts.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;


    public ResponseEntity<ProductDTO> getProductByQrCode(Long barCode) {
        Optional<Product> optionalProductAlreadyExist = productRepository.findByBarCode(barCode);

        if (optionalProductAlreadyExist.isPresent()) {
            ProductDTO productDTO = productMapper.toProductDTO(optionalProductAlreadyExist.get());
            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Product> createProduct(ProductDTO productDTO) {
        try {
            Optional<Product> optionalProductAlreadyExist = productRepository.findByBarCode(productDTO.getBarCode());

            if (optionalProductAlreadyExist.isPresent()) {
                throw new IllegalArgumentException("A product with this barcode already exists.");
            }
            Product product = productMapper.toProduct(productDTO);
            productRepository.save(product);

            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.SC_CONFLICT)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    //I DON'T AGREE WITH IT
    public ResponseEntity<Product> updateProduct(Long barCode, ProductDTO productDTO) {
        Product product = productRepository.findByBarCode(barCode)
                .orElseThrow(() -> new RuntimeException("Product not found with barCode: " + barCode));

        productMapper.updateProductFromDto(productDTO, product);

        if (productDTO.getCategory() != null && productDTO.getCategory().getId() != null) {

            Optional<Category> categoryProduct = categoryRepository.findById(productDTO.getCategory().getId());
            categoryProduct.ifPresent(product::setCategory);
        }

        productRepository.save(product);
        return ResponseEntity.ok(product);
    }


    public void deleteProduct(Long barCode) {
        Product existingProduct = productRepository.findByBarCode(barCode)
                .orElseThrow(() -> new RuntimeException("Product not found with barCode: " + barCode));

        productRepository.delete(existingProduct);
    }
}


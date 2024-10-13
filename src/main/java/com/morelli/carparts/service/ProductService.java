package com.morelli.carparts.service;

import com.morelli.carparts.mapper.ProductMapper;
import com.morelli.carparts.model.dto.ProductDTO;
import com.morelli.carparts.model.entity.Category;
import com.morelli.carparts.model.entity.Product;
import com.morelli.carparts.repository.CategoryRepository;
import com.morelli.carparts.repository.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;


    // Get product by barcode
    @Cacheable(value = "products", key = "#barCode")
    // FUNZIONA
    public ProductDTO getProductByBarCode(Long barCode) {
        Product product = productRepository.findByBarCode(barCode)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with barCode: " + barCode));
        return productMapper.toProductResponse(product);
    }

    @Cacheable(value = "products", key = "'all_products_' + #page + '_' + #size + '_' + #color + '_' + #productSize + '_' + #productName")
    // FUNZIONA
    public List<ProductDTO> getAll(int page, int size, String color, String productSize, String productName) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<Product> productPage = productRepository.findAllWithFilters(color, productSize, productName, pageable);
            return productPage.getContent().stream()
                    .map(productMapper::toProductResponse)
                    .toList();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching products.", e);
        }
    }

    // Add a new product
    // FUNZIONA
    public ProductDTO add(ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findByBarCode(productDTO.getBarCode());

        if (optionalProduct.isPresent()) {
            throw new EntityExistsException("This product already exists with barCode: " + productDTO.getBarCode());
        }

        if (productDTO.getCategory() == null || !categoryRepository.existsByName(productDTO.getCategory().getName())) {
            throw new EntityNotFoundException("Category not found.");
        }

        Product product = productMapper.mapToEntity(productDTO);
        product.setSaveTimestamp(Instant.now());
        Product savedProduct = productRepository.save(product);


        return productMapper.toProductResponse(savedProduct);
    }

    // Update existing product
    // FUNZIONA
    public ProductDTO update(Long barCode, ProductDTO productDTO) {
        Product existingProduct = productRepository.findByBarCode(barCode)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with barCode: " + barCode));

        if (productDTO.getCategory() != null && productDTO.getCategory().getId() != null) {
            Category existingCategory = categoryRepository.findById(productDTO.getCategory().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + productDTO.getCategory().getId()));
            existingProduct.setCategory(existingCategory);
        }

        Product updatedProduct = productMapper.updateProductFromDto(productDTO, existingProduct);
        updatedProduct.setUpdateTimestamp(Instant.now());
        Product savedProduct = productRepository.save(updatedProduct);


        return productMapper.toProductResponse(savedProduct);
    }

    // Delete product by barcode
    @CacheEvict(value = "products", key = "#barCode")
    // FUNZIONA
    public void delete(Long barCode) {
        Product existingProduct = productRepository.findByBarCode(barCode)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with barCode: " + barCode));

        productRepository.delete(existingProduct);
    }
}



package com.morelli.carparts.service;

import com.morelli.carparts.model.dto.CategoryDTO;
import com.morelli.carparts.model.dto.ProductDTO;
import com.morelli.carparts.model.dto.response.MovementResponse;
import com.morelli.carparts.model.dto.response.SaleDTO;
import com.morelli.carparts.model.dto.response.SaleProductResponse;
import com.morelli.carparts.model.entity.Category;
import com.morelli.carparts.model.entity.Product;
import com.morelli.carparts.model.entity.Sale;
import com.morelli.carparts.repository.CategoryRepository;
import com.morelli.carparts.repository.ProductRepository;
import com.morelli.carparts.repository.SaleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovementService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public MovementResponse getEntitiesFromLastWeek() {
        LocalDate today = LocalDate.now();

        Instant startOfWeek = today.minusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        List<Sale> sales = saleRepository.findBySaleDateBetween(startOfWeek, endOfDay);

        List<Product> savedProducts = productRepository.findBySaveTimestampBetween(startOfWeek, endOfDay);
        List<Product> updatedProducts = productRepository.findByUpdateTimestampBetween(startOfWeek, endOfDay);

        Set<Product> distinctProducts = new HashSet<>();
        distinctProducts.addAll(savedProducts);
        distinctProducts.addAll(updatedProducts);

        List<Category> savedCategories = categoryRepository.findBySaveTimestampBetween(startOfWeek, endOfDay);
        List<Category> updatedCategories = categoryRepository.findByUpdateTimestampBetween(startOfWeek, endOfDay);

        Set<Category> distinctCategories = new HashSet<>();
        distinctCategories.addAll(savedCategories);
        distinctCategories.addAll(updatedCategories);

        List<SaleDTO> saleDTOs = sales.stream()
                .map(sale -> {
                    List<SaleProductResponse> saleProductResponses = sale.getSaleProducts().stream()
                            .map(sp -> new SaleProductResponse(
                                    sp.getProduct().getBarCode(),
                                    sp.getProduct().getProductName(),
                                    sp.getQuantitySold(),
                                    sp.getQuantitySold() * sp.getProduct().getUnitPrice()
                            ))
                            .collect(Collectors.toList());

                    return new SaleDTO(sale.getId(), sale.getSaleDate(), sale.getTotalAmount(), saleProductResponses);
                })
                .collect(Collectors.toList());

        List<ProductDTO> productDTOs = distinctProducts.stream()
                .map(product -> new ProductDTO(
                        product.getBarCode(),
                        product.getProductName(),
                        new CategoryDTO(
                                product.getCategory().getId(),
                                product.getCategory().getName(),
                                product.getCategory().getSaveTimestamp(),
                                product.getCategory().getUpdateTimestamp()
                        ),
                        product.getDescription(),
                        product.getSize(),
                        product.getColor(),
                        product.getQuantity(),
                        product.getUnitCost(),
                        product.getUnitPrice(),
                        product.getSaveTimestamp(),
                        product.getUpdateTimestamp()
                ))
                .collect(Collectors.toList());

        List<CategoryDTO> categoryDTOs = distinctCategories.stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName(), category.getSaveTimestamp(), category.getUpdateTimestamp()))
                .collect(Collectors.toList());

        return new MovementResponse(saleDTOs, productDTOs, categoryDTOs);
    }
}

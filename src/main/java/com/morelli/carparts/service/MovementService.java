//package com.morelli.carparts.service;
//
//
//import com.morelli.carparts.model.dto.CategoryDTO;
//import com.morelli.carparts.model.dto.ProductDTO;
//import com.morelli.carparts.model.dto.response.MovementResponse;
//import com.morelli.carparts.model.dto.response.SaleDTO;
//import com.morelli.carparts.model.dto.response.SaleProductResponse;
//import com.morelli.carparts.model.entity.Category;
//import com.morelli.carparts.model.entity.Product;
//import com.morelli.carparts.model.entity.Sale;
//import com.morelli.carparts.model.entity.SaleProduct;
//import com.morelli.carparts.repository.CategoryRepository;
//import com.morelli.carparts.repository.ProductRepository;
//import com.morelli.carparts.repository.SaleProductRepository;
//import com.morelli.carparts.repository.SaleRepository;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@AllArgsConstructor
//public class MovementService {
//
//    @Autowired
//    private SaleRepository saleRepository;
//
//    @Autowired
//    private SaleProductRepository saleProductRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    public MovementResponse getEntitiesWithTodayTimestamp() {
//        LocalDate today = LocalDate.now();
//        Instant startOfDay = today.atStartOfDay(ZoneId.systemDefault()).toInstant();
//        Instant endOfDay = today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
//
//        List<Sale> sales = saleRepository.findBySaveTimestampBetween(startOfDay, endOfDay);
//        List<Product> products = productRepository.findByUpdateTimestampBetween(startOfDay, endOfDay);
//        List<Category> categories = categoryRepository.findByUpdateTimestampBetween(startOfDay, endOfDay);
//
//
//        List<SaleDTO> saleDTOs = sales.stream()
//                .map(sale -> {
//                    List<SaleProductResponse> saleProductResponses = sale.getSaleProducts().stream()
//                            .map(sp -> new SaleProductResponse(
//                                    sp.getProduct().getBarCode(),
//                                    sp.getProduct().getProductName(),
//                                    sp.getQuantitySold(),
//                                    sp.getQuantitySold() * sp.getProduct().getUnitPrice() // Calcola l'importo
//                            ))
//                            .collect(Collectors.toList());
//
//                    return new SaleDTO(sale.getId(), sale.getSaleDate(), sale.getTotalAmount(), saleProductResponses);
//                })
//                .collect(Collectors.toList());
//
//        List<ProductDTO> productDTOs = products.stream()
//                .map(product -> new ProductDTO(
//                        product.getBarCode(),
//                        product.getProductName(),
//                        new CategoryDTO(product.getCategory().getId(), product.getCategory().getName(), ),
//                        product.getDescription(),
//                        product.getSize(),
//                        product.getColor(),
//                        product.getQuantity(),
//                        product.getUnitCost(),
//                        product.getUnitPrice()
//                ))
//                .collect(Collectors.toList());
//
//        List<CategoryDTO> categoryDTOs = categories.stream()
//                .map(category -> new CategoryDTO(category.getId(), category.getName()))
//                .collect(Collectors.toList());
//
//        return new MovementResponse(saleDTOs, productDTOs, categoryDTOs);
//    }
//}
//

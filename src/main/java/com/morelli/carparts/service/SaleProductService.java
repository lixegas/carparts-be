package com.morelli.carparts.service;

import com.morelli.carparts.mapper.ProductMapper;
import com.morelli.carparts.mapper.SaleProductMapper;
import com.morelli.carparts.model.dto.ProductDTO;
import com.morelli.carparts.model.dto.request.SaleItemRequest;
import com.morelli.carparts.model.dto.request.SaleRequest;
import com.morelli.carparts.model.dto.response.SaleDTO;
import com.morelli.carparts.model.dto.response.SaleProductResponse;
import com.morelli.carparts.model.entity.Product;
import com.morelli.carparts.model.entity.Sale;
import com.morelli.carparts.model.entity.SaleProduct;
import com.morelli.carparts.repository.ProductRepository;
import com.morelli.carparts.repository.SaleProductRepository;
import com.morelli.carparts.repository.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class SaleProductService {

    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final SaleProductMapper saleProductMapper;
    private final SaleProductRepository saleProductRepository;

    @Cacheable(value = "sales", key = "#page + '_' + #size")
    public List<SaleDTO> getAllSales(int page, int size) {
        List<Sale> sales = saleRepository.findAll(PageRequest.of(page, size)).getContent();

        return sales.stream()
                .map(saleProductMapper::mapToResponse)
                .collect(Collectors.toList());
    }


    @Cacheable(value = "sales", key = "#saleId")
    public SaleDTO getSaleById(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new EntityNotFoundException("Sale not found with id: " + saleId));

        return saleProductMapper.mapToResponse(sale);
    }

    @Transactional
    public SaleDTO newSale(SaleRequest saleRequest) {
        Sale sale = new Sale();
        sale.setSaleDate(Instant.now());

        double totalSaleAmount = 0.0;

        for (SaleItemRequest request : saleRequest.getProducts()) {
            ProductDTO productDTO = productService.getProductByBarCode(request.getBarCode());
            if (productDTO == null) {
                throw new EntityNotFoundException("Product with barcode " + request.getBarCode() + " not found.");
            }

            int requestedQuantity = request.getQuantity();
            int availableQuantity = productDTO.getQuantity();

            if (availableQuantity < requestedQuantity) {
                throw new IllegalArgumentException("Not enough quantity for product: " + productDTO.getProductName());
            }

            productDTO.setQuantity(availableQuantity - requestedQuantity);

            Product product = productMapper.mapToEntity(productDTO);
            productRepository.save(product);

            SaleProduct saleProduct = new SaleProduct();
            saleProduct.setProduct(product);
            saleProduct.setQuantitySold(requestedQuantity);
            saleProduct.setSale(sale);
            saleProduct.setSaveTimestamp(Instant.now());

            double productAmount = requestedQuantity * product.getUnitPrice();
            totalSaleAmount += productAmount;

            sale.getSaleProducts().add(saleProduct);
        }

        sale.setTotalAmount(totalSaleAmount);
        saleRepository.save(sale);

        return saleProductMapper.mapToResponse(sale);
    }

    @CacheEvict(value = "sales", key = "#saleId")
    public void deleteSaleProducts(Long saleId) {
        List<SaleProduct> saleProducts = saleProductRepository.findBySaleId(saleId);
        if (saleProducts.isEmpty()) {
            throw new EntityNotFoundException("No SaleProducts found for Sale ID: " + saleId);
        }

        for (SaleProduct saleProduct : saleProducts) {
            ProductDTO productDTO = productService.getProductByBarCode(saleProduct.getProduct().getBarCode());
            if (productDTO == null) {
                throw new EntityNotFoundException("Product not found with barcode: " + saleProduct.getProduct().getBarCode());
            }

            int restoredStock = productDTO.getQuantity() + saleProduct.getQuantitySold();
            productDTO.setQuantity(restoredStock);

            Product product = productMapper.mapToEntity(productDTO);
            productRepository.save(product);
        }
        saleProductRepository.deleteAll(saleProducts);
        saleRepository.deleteById(saleId);
    }
}

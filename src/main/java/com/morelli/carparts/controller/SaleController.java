package com.morelli.carparts.controller;

import com.morelli.carparts.model.dto.request.SaleRequest;
import com.morelli.carparts.model.dto.response.SaleDTO;
import com.morelli.carparts.service.SaleProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/storage/sale")
@AllArgsConstructor
public class SaleController {

    private final SaleProductService saleProductService;

    // GET all sales
    @GetMapping("/")
    public ResponseEntity<List<SaleDTO>> getAllSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        List<SaleDTO> sales = saleProductService.getAllSales(page, size);
        return ResponseEntity.ok(sales);
    }

    // GET sale by ID
    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getSaleById(@PathVariable Long id) {
        SaleDTO sale = saleProductService.getSaleById(id);
        return ResponseEntity.ok(sale);
    }
    // Modifica del controller
    @PostMapping("/")
    public ResponseEntity<SaleDTO> createSale(@Valid @RequestBody SaleRequest saleRequest) {
        SaleDTO saleDTO = saleProductService.newSale(saleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saleDTO);
    }

    // DELETE a sale by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleProductService.deleteSaleProducts(id);
        return ResponseEntity.noContent().build();
    }
}


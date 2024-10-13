package com.morelli.carparts.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO implements Serializable {

    private Long saleId;
    private Instant saleDate;
    private Double totalAmount;
    private List<SaleProductResponse> products;
}


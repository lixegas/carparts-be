package com.morelli.carparts.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleProductResponse implements Serializable {

    private Long barCode;
    private String nameProduct;
    private Integer quantity;
    private Double amount;
}

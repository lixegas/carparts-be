package com.morelli.carparts.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VariantDTO {

    private String description;
    private String size;
    private String color;
    private Integer quantity;
    private Double unitCost;
    private Double unitPriceSale;
}

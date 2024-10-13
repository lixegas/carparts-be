package com.morelli.carparts.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private Long barCode;
    private String productName;
    private CategoryDTO category;
    private String description;
    private String size;
    private String color;
    private Integer quantity;
    private Double unitCost;
    private Double unitPrice;

}


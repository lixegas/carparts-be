package com.morelli.carparts.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

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
    private Instant saveTimestamp;
    private Instant updateTimestamp;
}


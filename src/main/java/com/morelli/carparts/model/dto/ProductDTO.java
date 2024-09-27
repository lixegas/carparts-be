package com.morelli.carparts.model.dto;


import com.morelli.carparts.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class ProductDTO {

    private Long barCode;
    private String productName;
    private Category category;
    private String description;
    private String size;
    private String color;
    private Integer quantity;
    private Double unitCost;
    private Double unitPrice;
}

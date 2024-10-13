package com.morelli.carparts.model.dto.response;

import com.morelli.carparts.model.dto.CategoryDTO;
import com.morelli.carparts.model.dto.ProductDTO;
import com.morelli.carparts.model.entity.Category;
import com.morelli.carparts.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementResponse {

    private List<SaleDTO> sales;
    private List<ProductDTO> products;
    private List<CategoryDTO> categories;
}

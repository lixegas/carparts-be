package com.morelli.carparts.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleItemRequest implements Serializable {

    private Long barCode;
    private Integer quantity;
}

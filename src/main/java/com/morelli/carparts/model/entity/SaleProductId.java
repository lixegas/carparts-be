package com.morelli.carparts.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;


@Data
@AllArgsConstructor
public class SaleProductId implements Serializable {
    private Long saleId;
    private Long barCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaleProductId)) return false;
        SaleProductId that = (SaleProductId) o;
        return Objects.equals(saleId, that.saleId) && Objects.equals(barCode, that.barCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, barCode);
    }
}

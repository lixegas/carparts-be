package com.morelli.carparts.mapper;

import com.morelli.carparts.model.dto.response.SaleDTO;
import com.morelli.carparts.model.entity.Sale;
import com.morelli.carparts.model.entity.SaleProduct;
import com.morelli.carparts.model.dto.response.SaleProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.Mapping;

@Named("saleProductMapper")
@Mapper(componentModel = "spring")
public interface SaleProductMapper {


        @Mappings({
                @Mapping(target = "saleId", source = "sale.id"),
                @Mapping(target = "saleDate", source = "sale.saleDate"),
                @Mapping(target = "totalAmount", source = "sale.totalAmount"),
                @Mapping(target = "products", source = "sale.saleProducts")
        })
        SaleDTO mapToResponse(Sale sale);



        @Mappings({
                @Mapping(target = "barCode", source = "saleProduct.product.barCode"),
                @Mapping(target = "nameProduct", source = "saleProduct.product.productName"),
                @Mapping(target = "quantity", source = "saleProduct.quantitySold"),
                @Mapping(target = "amount", expression = "java(saleProduct.getQuantitySold() * saleProduct.getProduct().getUnitPrice())")
        })
        SaleProductResponse mapToProductResponse(SaleProduct saleProduct);
}




package com.morelli.carparts.mapper;

import com.morelli.carparts.model.dto.ProductDTO;
import com.morelli.carparts.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Named("productMapper")
@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toProductDTO(Product product);
    Product toProduct(ProductDTO productDTO);

    @Mapping(target = "barCode", ignore = true)
    void updateProductFromDto(ProductDTO productDTO, @MappingTarget Product product);
}

package com.morelli.carparts.mapper;

import com.morelli.carparts.model.dto.ProductDTO;
import com.morelli.carparts.model.entity.Product;
import org.mapstruct.*;

@Named("productMapper")
@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toProductResponse(Product product);

    @Mappings({
            @Mapping(target = "barCode", source = "productDTO.barCode"),
            @Mapping(target = "productName", source = "productDTO.productName"),
            @Mapping(target = "category", source = "productDTO.category"),
            @Mapping(target = "description", source = "productDTO.description"),
            @Mapping(target = "size", source = "productDTO.size"),
            @Mapping(target = "color", source = "productDTO.color"),
            @Mapping(target = "quantity", source = "productDTO.quantity"),
            @Mapping(target = "unitCost", source = "productDTO.unitCost"),
            @Mapping(target = "unitPrice", source = "productDTO.unitPrice"),
    })
    Product mapToEntity(ProductDTO productDTO);

    @Mapping(target = "barCode", ignore = true)
    Product updateProductFromDto(ProductDTO productCreationRequest, @MappingTarget Product product);
}

package com.morelli.carparts.mapper;

import com.morelli.carparts.model.dto.CategoryDTO;
import com.morelli.carparts.model.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;


@Named("categoryMapper")
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toCategory(CategoryDTO categoryDTO);

    CategoryDTO toDTO(Category category);

    @Mapping(target = "id", ignore = true)
    void updateCategoryFromDTO(CategoryDTO categoryDTO, @MappingTarget Category category);
}

package org.ecom.mapper;

import org.ecom.dto.CategoryDTO;
import org.ecom.entity.Category;

public class CategoryMapper {

    public static CategoryDTO toDto(Category category) {
        return new CategoryDTO(
            category.getId(),
            category.getName()
        );
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }
}

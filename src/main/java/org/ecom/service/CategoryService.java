package org.ecom.service;

import org.ecom.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
	CategoryDTO createCategory(CategoryDTO categoryDTO);

	CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

	CategoryDTO getCategoryById(Long id);

	Page<CategoryDTO> getAllCategories(Pageable pageable);

	void deleteCategory(Long id);

	Long countProductsInCategory(Long categoryId);
}

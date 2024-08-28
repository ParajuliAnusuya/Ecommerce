package org.ecom.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.ecom.dto.CategoryDTO;
import org.ecom.entity.Category;
import org.ecom.exception.CategoryNotFoundException;
import org.ecom.mapper.CategoryMapper;
import org.ecom.repository.CategoryRepository;
import org.ecom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;	 

	 @Override
	    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
	        Category category = CategoryMapper.toEntity(categoryDTO);
	        Category savedCategory = categoryRepository.save(category);
	        return CategoryMapper.toDto(savedCategory);
	    }

	    @Override
	    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
	        Category category = categoryRepository.findById(id)
	                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id " + id));
	        category.setName(categoryDTO.getName());
	        Category updatedCategory = categoryRepository.save(category);
	        return CategoryMapper.toDto(updatedCategory);
	    }

	    @Override
	    public CategoryDTO getCategoryById(Long id) {
	        Category category = categoryRepository.findById(id)
	                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id " + id));
	        return CategoryMapper.toDto(category);
	    }

	    @Override
	    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
	        Page<Category> categories = categoryRepository.findAll(pageable);
	        List<CategoryDTO> categoryDTOs = categories.stream()
	                .map(CategoryMapper::toDto)
	                .collect(Collectors.toList());
	        return new PageImpl<>(categoryDTOs, pageable, categories.getTotalElements());
	    }

	    @Override
	    public void deleteCategory(Long id) {
	        Category category = categoryRepository.findById(id)
	                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id " + id));
	        categoryRepository.delete(category);
	    }

	    @Override
	    public Long countProductsInCategory(Long categoryId) {
	        return categoryRepository.countProductsInCategory(categoryId);
	    }
}
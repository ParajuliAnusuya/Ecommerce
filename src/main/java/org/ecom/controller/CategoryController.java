package org.ecom.controller;

import org.ecom.dto.ApiResponseDto;
import org.ecom.dto.CategoryDTO;
import org.ecom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<ApiResponseDto<?>> createCategory(@RequestBody CategoryDTO categoryDTO) {
		CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDto.builder().isSuccess(true)
				.message("Category created successfully").response(createdCategory).build());
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ApiResponseDto<?>> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {

		CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDto.builder().isSuccess(true)
				.message("Category updated successfully").response(updatedCategory).build());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ApiResponseDto<?>> getCategoryById(@PathVariable Long id) {
		CategoryDTO categoryDTO = categoryService.getCategoryById(id);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDto.builder().isSuccess(true)
				.message("List of category with id = " + id).response(categoryDTO).build());
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<CategoryDTO>> getAllCategories(Pageable pageable) {
		Page<CategoryDTO> categories = categoryService.getAllCategories(pageable);
		return ResponseEntity.ok(categories);
	}

	@DeleteMapping("/delete{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/products-count/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Long> countProductsInCategory(@PathVariable Long id) {
		Long productCount = categoryService.countProductsInCategory(id);
		return ResponseEntity.ok(productCount);
	}

}

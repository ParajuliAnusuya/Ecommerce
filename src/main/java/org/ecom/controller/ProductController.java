package org.ecom.controller;

import org.ecom.dto.ProductDTO;
import org.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
		Page<ProductDTO> products = productService.getAllProducts(pageable);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
		ProductDTO product = productService.getProductById(id);
		return ResponseEntity.ok(product);
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
		ProductDTO createdProduct = productService.createProduct(productDTO);
		return ResponseEntity.ok(createdProduct);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
		ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
		return ResponseEntity.ok(updatedProduct);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> softDeleteProduct(@PathVariable Long id) {
		productService.softDeleteProduct(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/restore/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> restoreProduct(@PathVariable Long id) {
		productService.restoreProduct(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/permanent/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> permanentlyDeleteProduct(@PathVariable Long id) {
		productService.permanentlyDeleteProduct(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Page<ProductDTO>> findProductsByName(@RequestParam String name, Pageable pageable) {
		Page<ProductDTO> products = productService.findProductsByName(name, pageable);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/category/{categoryId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<ProductDTO>> findProductsByCategoryId(@PathVariable Long categoryId, Pageable pageable) {
		Page<ProductDTO> products = productService.findProductsByCategoryId(categoryId, pageable);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/rate-greater-than")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Page<ProductDTO>> findProductsByRateGreaterThan(@RequestParam Double rate,
			Pageable pageable) {
		Page<ProductDTO> products = productService.findByRateGreaterThan(rate, pageable);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/rate-less-than")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Page<ProductDTO>> findProductsByRateLessThan(@RequestParam Double rate, Pageable pageable) {
		Page<ProductDTO> products = productService.findByRateLessThan(rate, pageable);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/available")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Page<ProductDTO>> findAvailableProducts(Pageable pageable) {
		Page<ProductDTO> products = productService.findAvailableProducts(pageable);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/rate-between")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Page<ProductDTO>> getProductsByRateBetween(@RequestParam Double minRate,
			@RequestParam Double maxRate, Pageable pageable) {
		Page<ProductDTO> products = productService.findProductsByRateBetween(minRate, maxRate, pageable);
		return ResponseEntity.ok(products);
	}

}

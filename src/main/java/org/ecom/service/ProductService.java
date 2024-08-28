package org.ecom.service;

import org.ecom.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

	Page<ProductDTO> getAllProducts(Pageable pageable);

	ProductDTO getProductById(Long id);

	ProductDTO createProduct(ProductDTO productDTO);

	ProductDTO updateProduct(Long id, ProductDTO productDTO);

	void softDeleteProduct(Long id);

	void restoreProduct(Long id);

	void permanentlyDeleteProduct(Long id);

	Page<ProductDTO> findProductsByName(String name, Pageable pageable);

	Page<ProductDTO> findProductsByCategoryId(Long categoryId, Pageable pageable);

	Page<ProductDTO> findAvailableProducts(Pageable pageable);

	Page<ProductDTO> findByRateGreaterThan(Double rate, Pageable pageable);

	Page<ProductDTO> findByRateLessThan(Double rate, Pageable pageable);

	Page<ProductDTO> findProductsByRateBetween(Double minRate, Double maxRate, Pageable pageable);
}

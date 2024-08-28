package org.ecom.service.impl;

import org.ecom.dto.ProductDTO;
import org.ecom.entity.Category;
import org.ecom.entity.Products;
import org.ecom.exception.BadRequestException;
import org.ecom.exception.CategoryNotFoundException;
import org.ecom.exception.ProductNotFoundException;
import org.ecom.mapper.ProductMapper;
import org.ecom.repository.CategoryRepository;
import org.ecom.repository.ProductRepo;
import org.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public Page<ProductDTO> getAllProducts(Pageable pageable) {
		Page<Products> productsPage = productRepo.findAllActiveProducts(pageable);
		return productsPage.map(ProductMapper::toDTO);
	}

	@Override
	public ProductDTO getProductById(Long id) {
		Products product = productRepo.findActiveProductById(id);
		if (product == null) {
			throw new ProductNotFoundException("Product not found or is deleted");
		}
		return ProductMapper.toDTO(product);
	}

	@Override
	public ProductDTO createProduct(ProductDTO productDTO) {
		if (productDTO.getProductName() == null || productDTO.getProductName().isEmpty()) {
			throw new BadRequestException("Product name is required");
		}

		Products product = ProductMapper.toEntity(productDTO, null);

		Category category = categoryRepo.findById(productDTO.getCategoryId()).orElseThrow(
				() -> new CategoryNotFoundException("Category not found with id " + productDTO.getCategoryId()));
		product.setCategory(category);

		Products savedProduct = productRepo.save(product);

		return ProductMapper.toDTO(savedProduct);
	}

	@Override
	public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
		Products product = productRepo.findActiveProductById(id);
		if (product == null) {
			throw new RuntimeException("Product not found or is deleted");
		}
		product.setProductName(productDTO.getProductName());
		product.setProductDescription(productDTO.getProductDescription());
		product.setRate(productDTO.getRate());
		product.setAvailableInStock(productDTO.getAvailableInStock());
		product.setUpdated_at(LocalDateTime.now());

		if (productDTO.getCategoryId() != null) {
			Category category = categoryRepo.findById(productDTO.getCategoryId()).orElseThrow(
					() -> new CategoryNotFoundException("Category not found with id " + productDTO.getCategoryId()));
			product.setCategory(category);
		}

		Products updatedProduct = productRepo.save(product);
		return ProductMapper.toDTO(updatedProduct);
	}

	@Override
	public void softDeleteProduct(Long id) {
		Products product = productRepo.findActiveProductById(id);
		if (product != null) {
			product.setDeleted_at(LocalDateTime.now());
			productRepo.save(product);
		} else {
			throw new ProductNotFoundException("Product not found or is already deleted");

		}
	}

	@Override
	public void restoreProduct(Long id) {
		Products product = productRepo.findById(id).orElse(null);
		if (product != null) {
			product.setDeleted_at(null);
			product.setUpdated_at(LocalDateTime.now());
			productRepo.save(product);
		} else {
			throw new ProductNotFoundException("Product not found or is not deleted");
		}
	}

	@Override
	public void permanentlyDeleteProduct(Long id) {
		Products product = productRepo.findById(id).orElse(null);
		if (product != null) {
			productRepo.delete(product);
		} else {
			throw new ProductNotFoundException("Product not found or is already deleted");
		}
	}

	@Override
	public Page<ProductDTO> findProductsByName(String name, Pageable pageable) {
		Page<Products> productsPage = productRepo.findByProductName(name, pageable);
		return productsPage.map(ProductMapper::toDTO);
	}

	@Override
	public Page<ProductDTO> findProductsByCategoryId(Long categoryId, Pageable pageable) {
		Page<Products> productsPage = productRepo.findByCategoryId(categoryId, pageable);
		return productsPage.map(ProductMapper::toDTO);
	}

	@Override
    public Page<ProductDTO> findByRateGreaterThan(Double rate, Pageable pageable) {
        Page<Products> productPage = productRepo.findByRateGreaterThan(rate, pageable);
        return productPage.map(ProductMapper::toDTO);
    }

    @Override
    public Page<ProductDTO> findByRateLessThan(Double rate, Pageable pageable) {
        Page<Products> productPage = productRepo.findByRateLessThan(rate, pageable);
        return productPage.map(ProductMapper::toDTO);
    }

	@Override
	public Page<ProductDTO> findAvailableProducts(Pageable pageable) {
		Page<Products> productsPage = productRepo.findAvailableProducts(pageable);
		return productsPage.map(ProductMapper::toDTO);
	}

	@Override
	public Page<ProductDTO> findProductsByRateBetween(Double minRate, Double maxRate, Pageable pageable) {
		Page<Products> products = productRepo.findByRateBetween(minRate, maxRate, pageable);
		return products.map(ProductMapper::toDTO);
	}

}

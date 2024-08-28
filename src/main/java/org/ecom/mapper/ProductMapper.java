package org.ecom.mapper;

import org.ecom.dto.ProductDTO;
import org.ecom.entity.Category;
import org.ecom.entity.Products;

public class ProductMapper {

    // Convert Products entity to ProductDTO
    public static ProductDTO toDTO(Products product) {
        if (product == null) {
            return null;
        }
        
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setProductName(product.getProductName());
        productDTO.setProductDescription(product.getProductDescription());
        productDTO.setRate(product.getRate());      
       
        if (product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getId());
        }
        
        productDTO.setAvailableInStock(product.getAvailableInStock());
        return productDTO;
    }

    // Convert ProductDTO to Products entity
    public static Products toEntity(ProductDTO productDTO, Category category) {
        if (productDTO == null) {
            return null;
        }

        Products product = new Products();
        product.setId(productDTO.getId());
        product.setProductName(productDTO.getProductName());
        product.setProductDescription(productDTO.getProductDescription());
        product.setRate(productDTO.getRate());

        product.setCategory(category);

        product.setAvailableInStock(productDTO.getAvailableInStock());
        return product;
    }
}


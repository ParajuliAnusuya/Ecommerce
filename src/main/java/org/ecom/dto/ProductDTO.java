package org.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name should not exceed 100 characters")
    private String productName;

    @NotBlank(message = "Product description is required")
    @Size(max = 1000, message = "Product description should not exceed 1000 characters")
    private String productDescription;

    @NotNull(message = "Product rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Rate must be greater than zero")
    private Double rate;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Available in stock is required")
    private Integer availableInStock;
}

package org.ecom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
	private long id;
	
	@NotBlank(message = "Product name is required")
	@Size(max = 100, message = "Product name should not exceed 100 characters")
	private String name;

}

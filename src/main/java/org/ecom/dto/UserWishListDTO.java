package org.ecom.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWishListDTO {
	
	private long id;
	@NotNull(message = "User ID is required")
	private Long userId;

	@NotNull(message = "Product ID is required")
	private Long productId;

	@NotNull(message = "Product name is required")
	private String productName;

}

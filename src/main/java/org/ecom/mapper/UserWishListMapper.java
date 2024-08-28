package org.ecom.mapper;

import org.ecom.dto.UserWishListDTO;
import org.ecom.entity.Products;
import org.ecom.entity.User;
import org.ecom.entity.UserWishList;

public class UserWishListMapper {

	public static UserWishListDTO toDto(UserWishList userWishList) {
		return new UserWishListDTO(userWishList.getId(), userWishList.getUser().getId(),
				userWishList.getProduct().getId(), userWishList.getProduct().getProductName());
	}

	public static UserWishList toEntity(UserWishListDTO userWishListDTO) {
		UserWishList userWishList = new UserWishList();

		userWishList.setId(userWishListDTO.getId());

		User user = new User();
		user.setId(userWishListDTO.getUserId());
		userWishList.setUser(user);

		Products product = new Products();
		product.setId(userWishListDTO.getProductId());
		userWishList.setProduct(product);

		return userWishList;
	}
}

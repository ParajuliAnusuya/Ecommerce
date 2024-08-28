package org.ecom.mapper;

import org.ecom.dto.UserCartItemsDTO;
import org.ecom.entity.Products;
import org.ecom.entity.User;
import org.ecom.entity.UserCartItems;

public class UserCartItemsMapper {

    // Convert UserCartItems entity to UserCartItemsDTO
    public static UserCartItemsDTO toDTO(UserCartItems userCartItems) {
        if (userCartItems == null) {
            return null;
        }

        UserCartItemsDTO dto = new UserCartItemsDTO();
        dto.setId(userCartItems.getId());
        dto.setUserId(userCartItems.getUser().getId());
        dto.setProductId(userCartItems.getProduct().getId());
        dto.setQuantity(userCartItems.getQuantity());
        dto.setIsChecked(userCartItems.getIsChecked());
        return dto;
    }

    // Convert UserCartItemsDTO to UserCartItems entity
    public static UserCartItems toEntity(UserCartItemsDTO dto, User user, Products product) {
        if (dto == null) {
            return null;
        }

        UserCartItems userCartItems = new UserCartItems();
        userCartItems.setId(dto.getId());
        userCartItems.setUser(user);
        userCartItems.setProduct(product);
        userCartItems.setQuantity(dto.getQuantity());
        userCartItems.setIsChecked(dto.getIsChecked());
        return userCartItems;
    }
}

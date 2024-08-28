package org.ecom.service;

import java.util.List;
import org.ecom.dto.UserCartItemsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface UserCartItemsService {
	
	UserCartItemsDTO createCartItem(UserCartItemsDTO userCartItemsDTO);
	
	Page<UserCartItemsDTO> getUserCartItemsById(Long id, Pageable pageable);

	Page<UserCartItemsDTO> getCartItemsByUserId(Long userId, Pageable pageable);

	Page<UserCartItemsDTO> getCartItemsByProductId(Long productId, Pageable pageable);

	Integer getTotalQuantityByUserId(Long userId);

	List<UserCartItemsDTO> getCheckedItemsByUserId(Long userId);

	UserCartItemsDTO updateCartItem(Long id, UserCartItemsDTO userCartItemsDTO);

	void softDeleteUserCartItem(Long id);

	void restoreUserCartItem(Long id);

	void permanentlyDeleteUserCartItem(Long id);
}

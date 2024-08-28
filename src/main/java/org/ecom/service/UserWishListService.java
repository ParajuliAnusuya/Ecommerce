package org.ecom.service;

import org.ecom.dto.UserWishListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface UserWishListService {
	UserWishListDTO addUserWishList(UserWishListDTO userWishListDTO);

	UserWishListDTO updateUserWishList(Long id, UserWishListDTO userWishListDTO);

	UserWishListDTO getUserWishListById(Long id);

	Page<UserWishListDTO> viewAllUserWishLists(Pageable pageable);

	void deleteUserWishList(Long id);

	Page<UserWishListDTO> getUserWishListsByUserId(Long userId, Pageable pageable);

	Page<UserWishListDTO> getUserWishListsByProductId(Long productId, Pageable pageable);

	Page<UserWishListDTO> getUserWishListsByUserIdAndProductId(Long userId, Long productId, Pageable pageable);

}

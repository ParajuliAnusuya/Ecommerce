package org.ecom.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.ecom.dto.UserWishListDTO;
import org.ecom.entity.Products;
import org.ecom.entity.User;
import org.ecom.entity.UserWishList;
import org.ecom.exception.ProductNotFoundException;
import org.ecom.exception.UserNotFoundException;
import org.ecom.exception.UserWishListNotFoundException;
import org.ecom.mapper.UserWishListMapper;
import org.ecom.repository.ProductRepo;
import org.ecom.repository.UserRepository;
import org.ecom.repository.UserWishListRepository;
import org.ecom.service.UserWishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class UserWishListServiceImpl implements UserWishListService {

	@Autowired
	private UserWishListRepository userWishListRepository;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ProductRepo productsRepo;

	@Override
	public UserWishListDTO addUserWishList(UserWishListDTO userWishListDTO) {
		UserWishList userWishList = UserWishListMapper.toEntity(userWishListDTO);
		UserWishList savedWishList = userWishListRepository.save(userWishList);
		return UserWishListMapper.toDto(savedWishList);
	}

	@Override
	public UserWishListDTO updateUserWishList(Long id, UserWishListDTO userWishListDTO) {
	    UserWishList userWishList = userWishListRepository.findById(id)
	            .orElseThrow(() -> new UserWishListNotFoundException("UserWishList not found with id " + id));

	    // Fetch the user and product from the database using their repositories
	    User user = userRepo.findById(userWishListDTO.getUserId())
	            .orElseThrow(() -> new UserNotFoundException("User not found with id " + userWishListDTO.getUserId()));

	    Products product = productsRepo.findById(userWishListDTO.getProductId())
	            .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + userWishListDTO.getProductId()));

	    // Set the fetched user and product
	    userWishList.setUser(user);
	    userWishList.setProduct(product);

	    UserWishList updatedWishList = userWishListRepository.save(userWishList);
	    return UserWishListMapper.toDto(updatedWishList);
	}

	@Override
	public UserWishListDTO getUserWishListById(Long id) {
		UserWishList userWishList = userWishListRepository.findById(id)
				.orElseThrow(() -> new UserWishListNotFoundException("UserWishList not found with id " + id));
		return UserWishListMapper.toDto(userWishList);
	}

	@Override
	public Page<UserWishListDTO> viewAllUserWishLists(Pageable pageable) {
		Page<UserWishList> userWishLists = userWishListRepository.findAll(pageable);
		List<UserWishListDTO> userWishListDTOs = userWishLists.stream().map(UserWishListMapper::toDto)
				.collect(Collectors.toList());
		return new PageImpl<>(userWishListDTOs, pageable, userWishLists.getTotalElements());
	}

	@Override
	public void deleteUserWishList(Long id) {
		UserWishList userWishList = userWishListRepository.findById(id)
				.orElseThrow(() -> new UserWishListNotFoundException("UserWishList not found with id " + id));
		userWishListRepository.delete(userWishList);
	}

	@Override
	public Page<UserWishListDTO> getUserWishListsByUserId(Long userId, Pageable pageable) {
		Page<UserWishList> userWishLists = userWishListRepository.findByUserId(userId, pageable);
		List<UserWishListDTO> userWishListDTOs = userWishLists.stream().map(UserWishListMapper::toDto)
				.collect(Collectors.toList());
		return new PageImpl<>(userWishListDTOs, pageable, userWishLists.getTotalElements());
	}

	@Override
	public Page<UserWishListDTO> getUserWishListsByProductId(Long productId, Pageable pageable) {
		Page<UserWishList> userWishLists = userWishListRepository.findByProductId(productId, pageable);
		List<UserWishListDTO> userWishListDTOs = userWishLists.stream().map(UserWishListMapper::toDto)
				.collect(Collectors.toList());
		return new PageImpl<>(userWishListDTOs, pageable, userWishLists.getTotalElements());
	}

	@Override
	public Page<UserWishListDTO> getUserWishListsByUserIdAndProductId(Long userId, Long productId, Pageable pageable) {
		Page<UserWishList> userWishLists = userWishListRepository.findByUserIdAndProductId(userId, productId, pageable);
		List<UserWishListDTO> userWishListDTOs = userWishLists.stream().map(UserWishListMapper::toDto)
				.collect(Collectors.toList());
		return new PageImpl<>(userWishListDTOs, pageable, userWishLists.getTotalElements());
	}
}

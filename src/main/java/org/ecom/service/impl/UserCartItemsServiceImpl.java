package org.ecom.service.impl;

import org.ecom.dto.UserCartItemsDTO;
import org.ecom.entity.Products;
import org.ecom.entity.User;
import org.ecom.entity.UserCartItems;
import org.ecom.exception.BadRequestException;
import org.ecom.exception.ProductNotFoundException;
import org.ecom.exception.UserCartItemNotFoundException;
import org.ecom.exception.UserNotFoundException;
import org.ecom.mapper.UserCartItemsMapper;
import org.ecom.repository.ProductRepo;
import org.ecom.repository.UserCartItemsRepo;
import org.ecom.repository.UserRepository;
import org.ecom.service.UserCartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserCartItemsServiceImpl implements UserCartItemsService {

    @Autowired
    private UserCartItemsRepo userCartItemsRepo;
    
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepository userRepository;
    
    
    
    @Override
    public UserCartItemsDTO createCartItem(UserCartItemsDTO userCartItemsDTO) {
        if (userCartItemsDTO.getUserId() == null || userCartItemsDTO.getProductId() == null) {
            throw new BadRequestException("User ID and Product ID are required");
        }

        User user = userRepository.findById(userCartItemsDTO.getUserId())
                                   .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userCartItemsDTO.getUserId()));

        Products product = productRepo.findById(userCartItemsDTO.getProductId())
                                      .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + userCartItemsDTO.getProductId()));

        UserCartItems cartItem = UserCartItemsMapper.toEntity(userCartItemsDTO, user, product);
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setCreated_at(LocalDateTime.now());
        cartItem.setUpdated_at(LocalDateTime.now());
        UserCartItems savedCartItem = userCartItemsRepo.save(cartItem);
        return UserCartItemsMapper.toDTO(savedCartItem);
    }

	@Override
	public Page<UserCartItemsDTO> getUserCartItemsById(Long id, Pageable pageable) {
		Page<UserCartItems> cartItemsPage = userCartItemsRepo.findUserCartItemsById(id, pageable);
        return cartItemsPage.map(UserCartItemsMapper::toDTO);
	}

    @Override
    public Page<UserCartItemsDTO> getCartItemsByUserId(Long userId, Pageable pageable) {
        Page<UserCartItems> cartItemsPage = userCartItemsRepo.findByUserId(userId, pageable);
        return cartItemsPage.map(UserCartItemsMapper::toDTO);
    }

    @Override
    public Page<UserCartItemsDTO> getCartItemsByProductId(Long productId, Pageable pageable) {
        Page<UserCartItems> cartItemsPage = userCartItemsRepo.findByProductId(productId, pageable);
        return cartItemsPage.map(UserCartItemsMapper::toDTO);
    }

    @Override
    public Integer getTotalQuantityByUserId(Long userId) {
        return userCartItemsRepo.findTotalQuantityByUserId(userId);
    }

    @Override
    public List<UserCartItemsDTO> getCheckedItemsByUserId(Long userId) {
        List<UserCartItems> checkedItems = userCartItemsRepo.findCheckedItemsByUserId(userId);
        return checkedItems.stream()
                .map(UserCartItemsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserCartItemsDTO updateCartItem(Long id, UserCartItemsDTO userCartItemsDTO) {
        UserCartItems cartItem = userCartItemsRepo.findById(id).orElse(null);
        if (cartItem == null || cartItem.getDeleted_at() != null) {
            throw new UserCartItemNotFoundException("Cart item not found or is deleted");
        }

        cartItem.setQuantity(userCartItemsDTO.getQuantity());
        cartItem.setIsChecked(userCartItemsDTO.getIsChecked());
        cartItem.setUpdated_at(LocalDateTime.now());
        UserCartItems updatedCartItem = userCartItemsRepo.save(cartItem);

        return UserCartItemsMapper.toDTO(updatedCartItem);
    }

    @Override
    public void softDeleteUserCartItem(Long id) {
        UserCartItems cartItem = userCartItemsRepo.findById(id).orElse(null);
        if (cartItem != null && cartItem.getDeleted_at() == null) {
            cartItem.setDeleted_at(LocalDateTime.now());
            userCartItemsRepo.save(cartItem);
        } else {
            throw new UserCartItemNotFoundException("Cart item not found or is already deleted");
        }
    }

    @Override
    public void restoreUserCartItem(Long id) {
        UserCartItems cartItem = userCartItemsRepo.findById(id).orElse(null);
        if (cartItem != null && cartItem.getDeleted_at() != null) {
            cartItem.setDeleted_at(null);
            cartItem.setUpdated_at(LocalDateTime.now());
            userCartItemsRepo.save(cartItem);
        } else {
            throw new UserCartItemNotFoundException("Cart item not found or is not deleted");
        }
    }

    @Override
    public void permanentlyDeleteUserCartItem(Long id) {
        UserCartItems cartItem = userCartItemsRepo.findById(id).orElse(null);
        if (cartItem != null) {
            userCartItemsRepo.delete(cartItem);
        } else {
            throw new UserCartItemNotFoundException("Cart item not found");
        }
    }


}

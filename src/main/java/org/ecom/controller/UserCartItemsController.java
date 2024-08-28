package org.ecom.controller;

import org.ecom.dto.UserCartItemsDTO;
import org.ecom.service.UserCartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
public class UserCartItemsController {

	@Autowired
	private UserCartItemsService userCartItemsService;

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<UserCartItemsDTO> createCartItem(@RequestBody UserCartItemsDTO userCartItemsDTO) {
		UserCartItemsDTO createdCartItem = userCartItemsService.createCartItem(userCartItemsDTO);
		return ResponseEntity.ok(createdCartItem);
	}

	@GetMapping("/user/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Page<UserCartItemsDTO>> getCartItemsByUserId(@PathVariable Long userId, Pageable pageable) {
		Page<UserCartItemsDTO> cartItems = userCartItemsService.getCartItemsByUserId(userId, pageable);
		return ResponseEntity.ok(cartItems);
	}

	@GetMapping("/id/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Page<UserCartItemsDTO>> getUserCartItemsById(@PathVariable Long id, Pageable pageable) {
		Page<UserCartItemsDTO> cartItems = userCartItemsService.getUserCartItemsById(id, pageable);
		return ResponseEntity.ok(cartItems);
	}

	@GetMapping("/product/{productId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Page<UserCartItemsDTO>> getCartItemsByProductId(@PathVariable Long productId,
			Pageable pageable) {
		Page<UserCartItemsDTO> cartItems = userCartItemsService.getCartItemsByProductId(productId, pageable);
		return ResponseEntity.ok(cartItems);
	}

	@GetMapping("/user/{userId}/total-quantity")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Integer> getTotalQuantityByUserId(@PathVariable Long userId) {
		Integer totalQuantity = userCartItemsService.getTotalQuantityByUserId(userId);
		return ResponseEntity.ok(totalQuantity);
	}

	@GetMapping("/user/{userId}/checked")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<List<UserCartItemsDTO>> getCheckedItemsByUserId(@PathVariable Long userId) {
		List<UserCartItemsDTO> checkedItems = userCartItemsService.getCheckedItemsByUserId(userId);
		return ResponseEntity.ok(checkedItems);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<UserCartItemsDTO> updateCartItem(@PathVariable Long id,
			@RequestBody UserCartItemsDTO userCartItemsDTO) {
		UserCartItemsDTO updatedCartItem = userCartItemsService.updateCartItem(id, userCartItemsDTO);
		return ResponseEntity.ok(updatedCartItem);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Void> softDeleteUserCartItem(@PathVariable Long id) {
		userCartItemsService.softDeleteUserCartItem(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/restore/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Void> restoreUserCartItem(@PathVariable Long id) {
		userCartItemsService.restoreUserCartItem(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/permanent/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Void> permanentlyDeleteUserCartItem(@PathVariable Long id) {
		userCartItemsService.permanentlyDeleteUserCartItem(id);
		return ResponseEntity.noContent().build();
	}
}

package org.ecom.controller;

import org.ecom.dto.UserWishListDTO;
import org.ecom.service.UserWishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user-wishlist")
public class UserWishListController {

    @Autowired
    private UserWishListService userWishListService;

    @GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<UserWishListDTO>> viewAllUserWishLists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserWishListDTO> userWishLists = userWishListService.viewAllUserWishLists(pageable);
        return new ResponseEntity<>(userWishLists, HttpStatus.OK);
    }

    @PostMapping
	@PreAuthorize("hasRole(ROLE_USER)")
    public ResponseEntity<UserWishListDTO> addUserWishList(@RequestBody UserWishListDTO userWishListDTO) {
        UserWishListDTO createdWishList = userWishListService.addUserWishList(userWishListDTO);
        return new ResponseEntity<>(createdWishList, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole(ROLE_USER)")
    public ResponseEntity<UserWishListDTO> getUserWishListById(@PathVariable long id) {
        UserWishListDTO userWishList = userWishListService.getUserWishListById(id);
        return new ResponseEntity<>(userWishList, HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole(ROLE_USER)")
    public ResponseEntity<UserWishListDTO> updateUserWishList(
            @PathVariable long id,
            @RequestBody UserWishListDTO userWishListDTO) {
        UserWishListDTO updatedWishList = userWishListService.updateUserWishList(id, userWishListDTO);
        return new ResponseEntity<>(updatedWishList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole(ROLE_USER)")
    public ResponseEntity<Void> deleteUserWishList(@PathVariable long id) {
        userWishListService.deleteUserWishList(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package org.ecom.exception;

public class UserWishListNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserWishListNotFoundException(String message) {
        super(message);
    }
}

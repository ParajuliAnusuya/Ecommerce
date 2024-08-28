package org.ecom.exception;

public class UserCartItemNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserCartItemNotFoundException(String message) {
        super(message);
    }
}
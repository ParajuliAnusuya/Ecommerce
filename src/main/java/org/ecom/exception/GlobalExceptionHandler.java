package org.ecom.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ecom.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<?>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {

        List<String> errorMessage = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.add(error.getDefaultMessage());
        });
        return ResponseEntity
                .badRequest()
                .body(
                        ApiResponseDto.builder()
                                .isSuccess(false)
                                .message("Failed: Please provide valid data.")
                                .response(errorMessage)
                                .build()
                );
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDto<?>> UserAlreadyExistsExceptionHandler(UserAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ApiResponseDto.builder()
                                .isSuccess(false)
                                .message(exception.getMessage())
                                .build()
                );
    }
    
    @ExceptionHandler(value = RoleNotFoundException.class)
    public ResponseEntity<ApiResponseDto<?>> RoleNotFoundExceptionHandler(RoleNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponseDto.builder()
                                .isSuccess(false)
                                .message(exception.getMessage())
                                .build()
                );
    }
    
    @ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<?> productNotFoundException(ProductNotFoundException ex, WebRequest request) {
		return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.builder()
                        .isSuccess(false)
                        .message(ex.getMessage())
                        .build());
	}

	@ExceptionHandler(UserCartItemNotFoundException.class)
	public ResponseEntity<?> UserCartItemNotFoundException(UserCartItemNotFoundException ex, WebRequest request) {
		return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.builder()
                        .isSuccess(false)
                        .message(ex.getMessage())
                        .build());
	}

	@ExceptionHandler(UserWishListNotFoundException.class)
	public ResponseEntity<?> userWishListNotFoundException(UserWishListNotFoundException ex, WebRequest request) {
		return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.builder()
                        .isSuccess(false)
                        .message(ex.getMessage())
                        .build());
	}

	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<?> categoryNotFoundException(CategoryNotFoundException ex, WebRequest request) {
		return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.builder()
                        .isSuccess(false)
                        .message(ex.getMessage())
                        .build());
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<?> orderNotFoundException(OrderNotFoundException ex, WebRequest request) {
		return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.builder()
                        .isSuccess(false)
                        .message(ex.getMessage())
                        .build());
	}

	@ExceptionHandler(OrderDetailsNotFoundException.class)
	public ResponseEntity<?> orderDetailsNotFoundException(OrderDetailsNotFoundException ex, WebRequest request) {
//		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(),
//				request.getDescription(false));
		return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.builder()
                        .isSuccess(false)
                        .message(ex.getMessage())
                        .build());
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequestException(BadRequestException ex, WebRequest request) {
		Map<String, String> errorDetails = new HashMap<>();
		errorDetails.put("error", ex.getMessage());
		return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.builder()
                        .isSuccess(false)
                        .message(ex.getMessage())
                        .build());
	}

}
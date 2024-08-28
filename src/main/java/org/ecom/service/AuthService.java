package org.ecom.service;

import org.ecom.dto.ApiResponseDto;
import org.ecom.dto.SignInRequestDto;
import org.ecom.dto.SignUpRequestDto;
import org.ecom.exception.RoleNotFoundException;
import org.ecom.exception.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<ApiResponseDto<?>> signUpUser(SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException, RoleNotFoundException;
    ResponseEntity<ApiResponseDto<?>> signInUser(SignInRequestDto signInRequestDto);

}
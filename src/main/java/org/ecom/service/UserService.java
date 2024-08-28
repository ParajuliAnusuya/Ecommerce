package org.ecom.service;

import org.ecom.dto.SignUpRequestDto;
import org.ecom.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	void save(User user);
	User updateUser(long id, SignUpRequestDto userdto);
}

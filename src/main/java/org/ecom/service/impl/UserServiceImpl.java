package org.ecom.service.impl;

import org.ecom.dto.SignUpRequestDto;
import org.ecom.entity.User;
import org.ecom.repository.UserRepository;
import org.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class UserServiceImpl implements UserService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	UserRepository userRepository;

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public User updateUser(long id, SignUpRequestDto userdto) {
		User user = userRepository.findById(id);
		if (user == null) {
			throw new RuntimeException("User not found");
		}
		user.setEmail(userdto.getEmail());
		user.setUsername(userdto.getUserName());
		user.setPassword(userdto.getPassword());

		user.getRoles().clear();
		
		//String rName = "";
//		for (String roleName : userdto.getRoles()) {
//			if(roleName == "admin") {
//				rName = ERole.ROLE_ADMIN;
//			} else {
//				rName = ERole.ROLE_USER;
//			}
//			Role role = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
//					.setParameter("name", rName).getResultList().stream().findFirst()
//					.orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
//			user.getRoles().add(role);
//		}
//		entityManager.persist(user);
		return user;

	}

}

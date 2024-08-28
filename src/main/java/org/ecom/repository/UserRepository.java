package org.ecom.repository;

import java.util.Optional;
import org.ecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//	Optional<User> findbyEmail(String email);
	Boolean existsByEmail(String email);
	Boolean existsByUsername(String username);
	Optional<User> findByEmail(String email);
	User findById(long id);
}

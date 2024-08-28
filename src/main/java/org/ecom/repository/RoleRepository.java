package org.ecom.repository;

import org.ecom.entity.Role;
import org.ecom.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
 Role findByName(ERole name);
}

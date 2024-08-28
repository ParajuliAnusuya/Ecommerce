package org.ecom.seeder;

import java.util.Arrays;
import java.util.List;
import org.ecom.entity.Role;
import org.ecom.enums.ERole;
import org.ecom.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;

@Component
public class RoleDataSeeder {
    @Autowired
    private RoleRepository roleRepository;

    @EventListener
    @Transactional
    public void LoadRoles(ContextRefreshedEvent event) {

        List<ERole> roles = Arrays.stream(ERole.values()).toList();

        for(ERole erole: roles) {
            if (roleRepository.findByName(erole)==null) {
            	Role r = new Role(erole);
                roleRepository.save(r);
            }
        }

    }

}
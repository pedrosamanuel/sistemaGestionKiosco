package com.inventario_ms.Security.loader;

import com.inventario_ms.Security.model.ERole;
import com.inventario_ms.Security.model.Role;
import com.inventario_ms.Security.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        createRoleIfNotFound(ERole.ROLE_USER);
        createRoleIfNotFound(ERole.ROLE_ADMIN);
        createRoleIfNotFound(ERole.ROLE_MODERATOR);
    }

    @Transactional
    public void createRoleIfNotFound(ERole roleName) {
        if (!roleRepository.findByName(roleName).isPresent()) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }
}
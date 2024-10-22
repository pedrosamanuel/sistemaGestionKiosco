package com.inventario_ms.Security.service;

import com.inventario_ms.Security.model.ERole;
import com.inventario_ms.Security.model.Role;
import com.inventario_ms.Security.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findByName(ERole eRole){
        return roleRepository.findByName(eRole);
    }
    public List<Role> getRoles (){
        List<Role> roles =  roleRepository.findAll();
        roles.removeIf(role -> role.getName().equals(ERole.ROLE_USER));
        return roles;
    }
}

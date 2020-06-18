package com.example.FashionStore.Services;

import com.example.FashionStore.Models.Role;
import com.example.FashionStore.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(Role newRole) {
        Role role = new Role(newRole.getRole());
        return roleRepository.save(role);
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByRole(roleName);
    }

    public Role getByRoleId(Integer roleId) {
        return roleRepository.findById(roleId).get();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}

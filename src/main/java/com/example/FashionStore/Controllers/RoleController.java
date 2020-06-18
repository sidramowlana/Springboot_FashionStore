package com.example.FashionStore.Controllers;


import com.example.FashionStore.Models.Role;
import com.example.FashionStore.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        Role persistedRole = roleService.createRole(role);
        return new ResponseEntity<>(persistedRole, HttpStatus.CREATED);
    }
}

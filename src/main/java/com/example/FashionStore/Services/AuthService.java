package com.example.FashionStore.Services;


import com.example.FashionStore.Models.Role;
import com.example.FashionStore.Models.User;
import com.example.FashionStore.Repositories.UserRepository;
import com.example.FashionStore.Request.AuthRequest;
import com.example.FashionStore.Response.JwtResponse;
import com.example.FashionStore.Response.MessageResponse;
import com.example.FashionStore.Security.Service.UserDetailsImpl;
import com.example.FashionStore.Security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private RoleService roleService;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;

    @Autowired
    public AuthService(RoleService roleService, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> registerUserService(User registerUser) {
        if (userRepository.existsByUsername(registerUser.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username already taken!"));
        }
        if (userRepository.existsByEmail(registerUser.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Email already taken!")));
        }

        //create a new user account after the checking
        User user = new User(
                registerUser.getUsername(),
                registerUser.getEmail(),
                registerUser.getAddress(),
                registerUser.getPhone(),
                passwordEncoder.encode(registerUser.getPassword()));

        Role role = roleService.getRoleByName("ROLE_USER");
        user.setRole(role);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    public ResponseEntity<?> loginUserService(AuthRequest authRequest) {
        if (!userRepository.existsByUsername(authRequest.getUsername())) {
            System.out.println("User name doesn't exist");
            return ResponseEntity.ok("User name doesn't exist");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken((authentication));
        Date expiretime = jwtUtils.expirationTime();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles, expiretime));
    }
}

package com.example.FashionStore.Controllers;

import com.example.FashionStore.Models.User;
import com.example.FashionStore.Request.AuthRequest;
import com.example.FashionStore.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User registerUser) {

        System.out.println(registerUser.getUsername());
      return authService.registerUserService(registerUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody AuthRequest authRequest) {
        System.out.println(authRequest.getUsername()+" "+authRequest.getPassword());
        return authService.loginUserService(authRequest);
    }
}
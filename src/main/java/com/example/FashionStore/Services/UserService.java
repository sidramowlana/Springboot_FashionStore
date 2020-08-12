package com.example.FashionStore.Services;

import com.example.FashionStore.Models.User;
import com.example.FashionStore.Repositories.OTPRepository;
import com.example.FashionStore.Repositories.UserRepository;
import com.example.FashionStore.Response.MessageResponse;
import com.example.FashionStore.Security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private JavaMailSender javaMailSender;
    @Value("${fashionStore.app.jwtSecret}")
    private String jwtSecret;

    @Value("${fashionStore.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    AuthService authService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private OTPRepository otpRepository;


    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder,
                       JavaMailSender javaMailSender,
                       AuthService authService,
                       AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils, OTPRepository otpRepository
    ) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.otpRepository = otpRepository;
    }

    public ResponseEntity<?> getUserByUserId(Integer userId) {
        if (userRepository.existsById(userId)) {
            Optional<User> user = userRepository.findById(userId);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User not found!!!"));
    }

    public ResponseEntity<?> getUserByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User email not available!!!"));
    }

    public ResponseEntity<MessageResponse> updateUserPasswordByUserId(Integer userId, String newPassword) {
        if (userRepository.existsById(userId)) {
            User user = userRepository.findById(userId).get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Successfully Updated"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("User not available!"));
        }

    }

    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username Not Found with username: " + username));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
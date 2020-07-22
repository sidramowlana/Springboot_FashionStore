package com.example.FashionStore.Services;

import com.example.FashionStore.Models.Address;
import com.example.FashionStore.Models.User;
import com.example.FashionStore.Repositories.AddressRepository;
import com.example.FashionStore.Repositories.UserRepository;
import com.example.FashionStore.Request.AuthRequest;
import com.example.FashionStore.Response.JwtResponse;
import com.example.FashionStore.Response.MessageResponse;
import com.example.FashionStore.Security.Service.UserDetailsImpl;
import com.example.FashionStore.Security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleService roleService;
    private AddressRepository addressRepository;
    private PasswordEncoder passwordEncoder;
    private JavaMailSender javaMailSender;
    @Value("${fashionStore.app.jwtSecret}")
    private String jwtSecret;

    @Value("${fashionStore.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    AuthService authService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;


    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder,
                       JavaMailSender javaMailSender,
                       AddressRepository addressRepository, AuthService authService,
                       AuthenticationManager authenticationManager, JwtUtils jwtUtils
    ) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.addressRepository = addressRepository;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
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

    public ResponseEntity<?> addUserAddressByUserId(Address newAddress, HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        if (addressRepository.existsByAddressAndCityAndUserUserId(newAddress.getAddress(), newAddress.getCity(), user.getUserId())) {
            Address address = addressRepository.findByAddressAndCityAndUserUserId(newAddress.getAddress(), newAddress.getCity(), user.getUserId());
            addressRepository.delete(address);
            return ResponseEntity.ok().body("removed");
        } else {
            Address address = new Address();
            address.setAddress(newAddress.getAddress());
            address.setPostalCode(newAddress.getPostalCode());
            address.setCity(newAddress.getCity());
            address.setUser(user);
            addressRepository.save(address);
            return ResponseEntity.ok().body("Successfully added");
        }

    }

    public Address getUserAddressByUserId(Integer userId) {
        Address address = addressRepository.findByUserUserId(userId);
        System.out.println(address.getAddress());
        return address;
    }

}

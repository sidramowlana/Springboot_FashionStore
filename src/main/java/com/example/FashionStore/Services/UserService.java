package com.example.FashionStore.Services;

import com.example.FashionStore.Models.Address;
import com.example.FashionStore.Models.User;
import com.example.FashionStore.Repositories.AddressRepository;
import com.example.FashionStore.Repositories.UserRepository;
import com.example.FashionStore.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder,
                       JavaMailSender javaMailSender, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.addressRepository = addressRepository;
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

    public ResponseEntity<?> updateUserPasswordByUserId(Integer userId, String newPassword) {
        if (userRepository.existsById(userId)) {
            User user = userRepository.findById(userId).get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return ResponseEntity.ok(user);
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

    public ResponseEntity<?> updateUserById(Integer userId, User newUser) {
        if (userRepository.existsById(userId)) {
            User user = userRepository.findById(userId).get();
            user.setUsername(newUser.getUsername());
            user.setEmail(newUser.getEmail());
            user.setPhone(newUser.getPhone());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepository.save(user);
        }
        return ResponseEntity.ok(new MessageResponse("User Successfully Updated"));
    }

    public ResponseEntity<?> addUserAddressByUserId(Address newAddress,HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        if(addressRepository.existsByAddressAndCityAndUserUserId(newAddress.getAddress(),newAddress.getCity(),user.getUserId())) {
           Address address = addressRepository.findByAddressAndCityAndUserUserId(newAddress.getAddress(),newAddress.getCity(),user.getUserId());
                   addressRepository.delete(address);
            return ResponseEntity.ok().body("removed");
        }else {
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

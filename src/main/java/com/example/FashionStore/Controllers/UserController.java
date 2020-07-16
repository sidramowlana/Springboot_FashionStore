package com.example.FashionStore.Controllers;

import com.example.FashionStore.Models.Address;
import com.example.FashionStore.Models.User;
import com.example.FashionStore.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RequestMapping("api/users")
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/user/{userId}")
    public ResponseEntity<?> getAUser(@PathVariable Integer userId) {
        return userService.getUserByUserId(userId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/updateUser/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserProfileById(@PathVariable Integer userId, @RequestBody User newUser) {
        return userService.updateUserById(userId, newUser);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/updatePassword/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserPasswordByUserId(@PathVariable Integer userId, @RequestBody String  newPassword) {
        return userService.updateUserPasswordByUserId(userId, newPassword);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("user/address/{userId}")
    public Address getUserAddressByUserId(@PathVariable Integer userId)
    {
        return userService.getUserAddressByUserId(userId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("user/add-address")
    public ResponseEntity<?> addUserAddressByUserId(@RequestBody Address newAddress, HttpServletRequest request)
    {
        return userService.addUserAddressByUserId(newAddress,request);
    }

}

package com.example.FashionStore.Controllers;

import com.example.FashionStore.Response.MessageResponse;
import com.example.FashionStore.Services.OTPService;
import com.example.FashionStore.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/otp")
@RestController
public class OTPController {

    private OTPService otpService;

    @Autowired
    public OTPController(OTPService otpService) {
        this.otpService = otpService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/request-password-change/{userId}")
    public ResponseEntity<MessageResponse> generateOPTSendEmail(@PathVariable Integer userId, @RequestParam String email) {
        return otpService.generateOPTSendEmail(userId, email);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/valid-check/{userId}")
    public ResponseEntity<MessageResponse> checkOTPAvailable(@PathVariable Integer userId, @RequestParam Integer otpNumber) {
        System.out.println(userId+" = "+otpNumber);
        return otpService.checkOTPAvailable(otpNumber);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/reset/{userId}")
    public ResponseEntity<MessageResponse> resetPassword(@PathVariable Integer userId, @RequestParam String newPassword) {
        System.out.println(userId+" = "+newPassword);
        return otpService.resetPassword(newPassword,userId);
    }

}

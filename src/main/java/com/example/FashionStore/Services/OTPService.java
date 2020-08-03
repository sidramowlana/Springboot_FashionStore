package com.example.FashionStore.Services;

import com.example.FashionStore.Models.OTP;
import com.example.FashionStore.Models.User;
import com.example.FashionStore.Repositories.OTPRepository;
import com.example.FashionStore.Repositories.UserRepository;
import com.example.FashionStore.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
public class OTPService {
    private OTPRepository otpRepository;
    private JavaMailSender javaMailSender;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;



    @Autowired
    public OTPService(OTPRepository otpRepository, JavaMailSender javaMailSender, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.otpRepository = otpRepository;
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }


    public ResponseEntity<MessageResponse> generateOPTSendEmail(Integer userId, String email) {
        if (otpRepository.existsByUserUserId(userId)) {
            OTP otp = otpRepository.findByUserUserId(userId);
            int otpNum = generateOTP();
            otp.setOtpNumber(otpNum);
            otp.setUser(otp.getUser());
            otp.setExpiryDate(1);
            otpRepository.save(otp);
            sendEmail(otp);
            return ResponseEntity.ok().body(new MessageResponse("Success: Email has been sent to you!"));
        } else {
            User user = userRepository.findById(userId).get();
            OTP otp = new OTP();
            int otpNum = generateOTP();
            otp.setOtpNumber(otpNum);
            otp.setUser(user);
            otp.setExpiryDate(1);
            otpRepository.save(otp);
            sendEmail(otp);
            return ResponseEntity.ok().body(new MessageResponse("Success: Email has been sent to you!"));
        }

    }
    public void sendEmail(OTP otp){
        //send an email with the token to that user
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(otp.getUser().getEmail());
        simpleMailMessage.setSubject("Password Reset Request");
        simpleMailMessage.setText("To reset your password, type the given OTP =" + otp.getOtpNumber());
        javaMailSender.send(simpleMailMessage);
    }

    public int generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return otp;
    }

    public ResponseEntity<MessageResponse> checkOTPAvailable(Integer otpNumber) {
        if (otpRepository.existsByOtpNumber(otpNumber)) {
            OTP otp = otpRepository.findByOtpNumber(otpNumber);
            Date today = Calendar.getInstance().getTime();
            System.out.println(today);
            if ((otp.getExpiryDate()).compareTo(today) > 0) {
                return ResponseEntity.ok().body(new MessageResponse("Valid OTP"));
            } else {
                return ResponseEntity.ok().body(new MessageResponse(("OTP is expired. Please generate a new one!")));
            }
        } else {
            return ResponseEntity.ok().body(new MessageResponse("Please generate a new one"));
        }
    }

    public ResponseEntity<MessageResponse> resetPassword(String password, Integer userId){
        User user = userRepository.findById(userId).get();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return ResponseEntity.ok().body(new MessageResponse("Password successfully updated"));
    }
}


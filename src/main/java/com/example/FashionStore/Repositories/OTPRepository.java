package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.OTP;
import com.example.FashionStore.Models.Orders;
import com.example.FashionStore.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OTPRepository extends JpaRepository<OTP, Integer> {
    OTP findByOtpNumber(Integer otpNumber);

    boolean existsByOtpNumber(Integer otpNumber);

    OTP findByUserUserId(Integer userId);

    boolean existsByUserUserId(Integer userId);
}

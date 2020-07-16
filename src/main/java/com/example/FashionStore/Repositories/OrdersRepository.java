package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.Orders;
import com.example.FashionStore.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByUserAndStatus(User user, String status);
}

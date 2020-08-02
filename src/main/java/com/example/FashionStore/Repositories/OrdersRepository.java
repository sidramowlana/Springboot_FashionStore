package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.Orders;
import com.example.FashionStore.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByUserAndStatus(User user, String status);
    List<Orders> findByUserAndStatusOrderByOrderIdDesc(User user, String status);

    List<Orders> findByStatus(String status);
}

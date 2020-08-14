package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Models.CartOrders;
import com.example.FashionStore.Models.Orders;
import com.example.FashionStore.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartOrdersRepository extends JpaRepository<CartOrders, Integer> {
    List<CartOrders> findByOrders(Orders orders);

    List<CartOrders> findByOrdersUserUserIdOrderByCardOrderId(Integer userId);
}

package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Models.CartOrders;
import com.example.FashionStore.Models.Orders;
import com.example.FashionStore.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartOrdersRepository extends JpaRepository<CartOrders, Integer> {
    List<CartOrders> findByOrders(Orders orders);

    Product findByCartProduct(Product product);

    List<CartOrders> findByOrdersUserUserId(Integer userId);
}

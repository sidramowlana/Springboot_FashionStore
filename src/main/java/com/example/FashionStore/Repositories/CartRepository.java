package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findByUser(String userName);

    Cart findByProduct(String productName);

    boolean existsByProduct(String productName);

    List<Cart> findByUserUserId(Integer userId);

    void deleteByProduct(String productName);
}

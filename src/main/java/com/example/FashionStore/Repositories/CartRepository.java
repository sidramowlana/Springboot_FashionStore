package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Models.Orders;
import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findByProduct(Product product);

    Cart findByProductProductId(Integer productId);

//    List<Cart> findByOrders(Orders orders);

    Cart findByProductProductIdAndSizeAndUserUserId(Integer productId, String size, Integer userId);

    boolean existsByUser(User user);

    boolean existsByProductAndSizeAndIsPurchased(Product product, String size, boolean isPurchased);

    boolean existsByProductProductIdAndUserUserId(Integer productId, Integer userId);

    boolean existsByProductProductIdAndSizeAndUserUserId(Integer productId, String size, Integer userId);

    Cart findByProductAndSize(Product product, String size);

    List<Cart> findByUserAndIsPurchased(User user, boolean isPurchased);

    void deleteByProduct(String productName);
}

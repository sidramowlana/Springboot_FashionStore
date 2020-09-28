package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findByProductProductIdAndSizeAndUserUserId(Integer productId, String size, Integer userId);

    boolean existsByUserAndProductAndSizeAndIsPurchased(User user, Product product, String size, boolean isPurchased);

    boolean existsByProductProductIdAndSizeAndUserUserId(Integer productId, String size, Integer userId);

    Cart findByUserAndProductAndSize(User user, Product product, String size);

    List<Cart> findByUserAndIsPurchased(User user, boolean isPurchased);

    List<Cart> findByUserAndIsPurchasedOrderByCartIdDesc(User user, boolean isPurchased);

    List<Cart> findByUserUserIdAndIsPurchased(int id, boolean isPurchased);

}

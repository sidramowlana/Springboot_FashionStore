package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findByProductProductIdAndSizeAndUserUserId(Integer productId, String size, Integer userId);

    boolean existsByProductAndSizeAndIsPurchased(Product product, String size, boolean isPurchased);

    boolean existsByProductProductIdAndSizeAndUserUserId(Integer productId, String size, Integer userId);

    Cart findByProductAndSize(Product product, String size);

    List<Cart> findByUserAndIsPurchased(User user, boolean isPurchased);
    List<Cart> findByUserAndIsPurchasedOrderByCartIdDesc(User user, boolean isPurchased);
//    List<Cart> findAllByOrderByTagIdDesc();

}

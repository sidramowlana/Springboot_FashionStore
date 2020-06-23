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

    Cart findByProduct(Product product);

    Cart findByProductProductId(Integer productId);
    Cart findByProductProductIdAndSizeAndUserUserId(Integer productId,String size, Integer userId);

    boolean existsByProduct(Product product);

    boolean existsByProductAndSize(Product product, String size);

    boolean existsByProductProductIdAndUserUserId(Integer productId, Integer userId);
    boolean existsByProductProductIdAndSizeAndUserUserId(Integer productId,String size, Integer userId);

    Cart findByProductAndSize(Product product, String size);

    boolean existsByProductProductId(Integer productId);

    boolean existsByUserUserId(Integer userId);

    List<Cart> findByUser(User user);

    void deleteByProduct(String productName);
}

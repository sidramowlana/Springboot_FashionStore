package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    Wishlist findByUser(String userName);

    Wishlist findByProduct(String productName);

    boolean existsByProduct(String productName);

    List<Wishlist> findByUserUserId(Integer userId);

    void deleteByProduct(String productName);
}

package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.User;
import com.example.FashionStore.Models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    List<Wishlist> findByUser(User user);
    List<Wishlist> findByUserOrderByWishlistIdDesc(User user);

    Wishlist findByProduct(Product product);

    boolean existsByUser(User user);

    boolean existsByProduct(Product product);

    List<Wishlist> findByUserUserId(Integer userId);

    Wishlist findByProductAndUser(Product product, User user);
}

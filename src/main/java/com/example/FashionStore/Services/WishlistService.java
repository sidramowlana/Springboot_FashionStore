package com.example.FashionStore.Services;


import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.User;
import com.example.FashionStore.Models.Wishlist;
import com.example.FashionStore.Repositories.ProductRepository;
import com.example.FashionStore.Repositories.UserRepository;
import com.example.FashionStore.Repositories.WishlistRepository;
import com.example.FashionStore.Request.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {
    private WishlistRepository wishlistRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> addNewWishlistItem(Integer productId, Wishlist newWishlistItem, HttpServletRequest request) {
        Product product = productRepository.findById(productId).get();
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        Wishlist wishlist = new Wishlist();
        newWishlistItem.setProduct(product);
        newWishlistItem.setUser(user);
        wishlistRepository.save(wishlist);
        return ResponseEntity.ok().body("");
    }


    public void deleteWishlistItem(Integer id) {
        if (wishlistRepository.existsById(id)) {
            Wishlist wishlist = wishlistRepository.findById(id).get();
            wishlistRepository.delete(wishlist);
        }
    }

    public List<Wishlist> getAllWishlistItems(HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        List<Wishlist> wishlist = wishlistRepository.findByUserUserId(user.getUserId());
        return wishlist;
    }
}

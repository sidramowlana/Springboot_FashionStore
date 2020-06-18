package com.example.FashionStore.Services;


import com.example.FashionStore.Models.User;
import com.example.FashionStore.Models.Wishlist;
import com.example.FashionStore.Repositories.UserRepository;
import com.example.FashionStore.Repositories.WishlistRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    private WishlistRepository wishlistRepository;
    private UserRepository userRepository;

    public WishlistService(WishlistRepository wishlistRepository, UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> addNewWishlistItem(Integer userId, Wishlist newWishlistItem) {
        User user = userRepository.findById(userId).get();
        Wishlist wishlist = new Wishlist();
        newWishlistItem.getProduct();
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

    public List<Wishlist> getAllWishlistItems(Integer userId) {
        List<Wishlist> wishlist = wishlistRepository.findByUserUserId(userId);
        return wishlist;
    }
}

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

    public ResponseEntity<?> onWishlistItem(Integer productId, HttpServletRequest request) {
        Product product = productRepository.findById(productId).get();
        System.out.println(product);
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        System.out.println(user);
        if (wishlistRepository.existsByProduct(product) && wishlistRepository.existsByUser(user)) {
            System.out.println("It is favourited so now gonna remove");
            Wishlist deleteWishlist = wishlistRepository.findByProductAndUser(product,user);
            System.out.println(deleteWishlist);
            wishlistRepository.delete(deleteWishlist);
            return ResponseEntity.ok().body("Removed from your wishlist");
        } else {
            System.out.println("it is not favourited so gonna favourite now");
            Wishlist wishlist = new Wishlist();
            wishlist.setProduct(product);
            wishlist.setUser(user);
//            wishlist.setFavourite(true);
            wishlistRepository.save(wishlist);
            return ResponseEntity.ok().body("Added to your wishlist");

        }
    }

    public List<Wishlist> getAllWishlistItemsByUserToken(HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        List<Wishlist> wishlist = wishlistRepository.findByUser(user);
        return wishlist;
    }

    public ResponseEntity<?> getWishlistProduct(Integer productId, HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        Product product = productRepository.findById(productId).get();
        if (wishlistRepository.existsByProduct(product) && wishlistRepository.existsByUser(user)) {
            Wishlist wishlist = wishlistRepository.findByProduct(product);
            return ResponseEntity.ok().body(wishlist);
        } else {
            return ResponseEntity.ok().body("No favourites product");
        }
    }


}

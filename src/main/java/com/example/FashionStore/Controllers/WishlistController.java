package com.example.FashionStore.Controllers;

import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.Wishlist;
import com.example.FashionStore.Response.MessageResponse;
import com.example.FashionStore.Services.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/wishlist")
@RestController
public class WishlistController {
    private WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/add-wishlist/{productId}")
    public ResponseEntity<?> addWishlist(@PathVariable Integer productId, HttpServletRequest request) {
        return wishlistService.onWishlistItem(productId, request);
    }

//wishlist page displaying all favorutie product of a user
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/wishlistAll")
    public List<Wishlist> getAllWishlistItemsByUser(HttpServletRequest httpServletRequest) {
        return wishlistService.getAllWishlistItemsByUserToken(httpServletRequest);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/product/{productId}")
    public ResponseEntity<?> getWishlistProduct(@PathVariable Integer productId, HttpServletRequest request) {
        return wishlistService.getWishlistProduct(productId, request);
    }

}

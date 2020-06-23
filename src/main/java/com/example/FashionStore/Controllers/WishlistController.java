package com.example.FashionStore.Controllers;


import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.Wishlist;
import com.example.FashionStore.Request.AuthRequest;
import com.example.FashionStore.Services.WishlistService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        System.out.println("add wishlist: " + productId + " = " + request.getUserPrincipal().getName());
        return wishlistService.onWishlistItem(productId, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/wishlistAll")
    public List<Wishlist> getAllWishlistItemByProductId(HttpServletRequest httpServletRequest) {
        System.out.println("http: " + httpServletRequest.getUserPrincipal().getName());
        return wishlistService.getAllWishlistItemsByUserToken(httpServletRequest);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/product/{productId}")
    public ResponseEntity<?> getWishlistProduct(@PathVariable Integer productId, HttpServletRequest request) {
        return wishlistService.getWishlistProduct(productId, request);
    }

}

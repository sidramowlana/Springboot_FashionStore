package com.example.FashionStore.Controllers;


import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.Wishlist;
import com.example.FashionStore.Request.AuthRequest;
import com.example.FashionStore.Services.WishlistService;
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
    public ResponseEntity<?> addWishlist(@PathVariable Integer productId, @RequestBody Wishlist newWishlist, HttpServletRequest request) {
        System.out.println(productId);
        return wishlistService.addNewWishlistItem(productId, newWishlist, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/wishlistAll/")
    public List<Wishlist> getAllWishlistItemByProductId(HttpServletRequest httpServletRequest) {
        return wishlistService.getAllWishlistItems(httpServletRequest);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/Wishlist/{WishlistId}", method = RequestMethod.DELETE)
    public void deleteWishlistItemByUserId(@PathVariable Integer WishlistId) {
        wishlistService.deleteWishlistItem(WishlistId);
    }


}

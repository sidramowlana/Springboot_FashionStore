package com.example.FashionStore.Controllers;


import com.example.FashionStore.Models.Wishlist;
import com.example.FashionStore.Services.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/wishlist")
@RestController
public class WishlistController {
    private WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/createWishlist/{userId}")
    public ResponseEntity<?> addWishlist(@PathVariable Integer userId, @RequestBody Wishlist newWishlist) {
        System.out.println(userId);
        return wishlistService.addNewWishlistItem(userId, newWishlist);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/wishlistAll/{userId}")
    public List<Wishlist> getAllWishlistItemByUserId(@PathVariable Integer userId) {
        return wishlistService.getAllWishlistItems(userId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/Wishlist/{WishlistId}", method = RequestMethod.DELETE)
    public void deleteWishlistItemByUserId(@PathVariable Integer WishlistId) {
        wishlistService.deleteWishlistItem(WishlistId);

    }
}

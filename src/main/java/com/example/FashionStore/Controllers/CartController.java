package com.example.FashionStore.Controllers;


import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("api/cart")
@RestController
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/createCart/{userId}")
    public ResponseEntity<?> addCart(@PathVariable Integer userId, @RequestBody Cart newCart) {
        System.out.println(userId);
        return cartService.addNewCartItem(userId, newCart);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/cartAll/{userId}")
    public List<Cart> getAllCartItemByUserId(@PathVariable Integer userId) {
        return cartService.getAllCartItems(userId);
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
//    @GetMapping(value = "/cartAll/cart/{cartId}")
//    public Cart getAllCartItemByCartId(@PathVariable Integer cartId) {
//        return cartService.getCartByCartId(cartId);
//    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/cart/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCartItemByUserId(@PathVariable Integer userId, @RequestBody Cart cart) {
        return cartService.updateCartItem(userId,cart);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/cart/{cartId}", method = RequestMethod.DELETE)
    public void deleteCartItemByUserId(@PathVariable Integer cartId) {
        cartService.deleteCartItem(cartId);
    }
}
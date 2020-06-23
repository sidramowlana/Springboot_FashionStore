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
    @PostMapping(value = "/add-cart/{productId}")
    public ResponseEntity<?> addCart(@PathVariable Integer productId,@RequestParam Integer quantity,@RequestParam String size,@RequestParam Double total, HttpServletRequest request) {
        System.out.println(productId);
        return cartService.addNewCartItem(productId,quantity,size,total, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/cartAll")
    public List<Cart> getAllCartItemByUserId(HttpServletRequest request) {
        return cartService.getAllCartItems(request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/delete-cart/{productId}")
    public List<Cart> getAllCartItemByCartId(@PathVariable Integer productId,@RequestParam String size, HttpServletRequest request) {
       return cartService.deleteCartProductByProductId(productId,size,request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/cart-update/product/{productId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCartItemByUserId(@PathVariable Integer productId, @RequestBody Cart cart) {
        return cartService.updateCartItem(productId,cart);
    }
  }
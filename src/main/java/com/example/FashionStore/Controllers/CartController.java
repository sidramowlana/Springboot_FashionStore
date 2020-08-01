package com.example.FashionStore.Controllers;


import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Response.MessageResponse;
import com.example.FashionStore.Services.CartService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/cart")
@RestController
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/add-cart/{productId}")
    public ResponseEntity<?> addCart(@PathVariable Integer productId,@RequestParam Integer quantity,
                                     @RequestParam String size,@RequestParam Double total, HttpServletRequest request) {
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
    @RequestMapping(value = "/update-cart/{cartId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCartItemByUserId(@PathVariable Integer cartId, @RequestParam Integer quantity) {
        return cartService.updateCartItem(cartId,quantity);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/cartAll/{userId}")
    public List<Cart> getAllUserIsPurchaseCartProducts(@PathVariable Integer userId, @RequestParam boolean isPurchased, HttpServletRequest request) {
        return cartService.getAllPendingCartProducts(userId,isPurchased,request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping(value="/delete/{cartId}")
    public List<Cart> deleteCartId(@PathVariable Integer cartId, HttpServletRequest request){
        System.out.println(cartId);
        return cartService.deleteCartIdByCartId(cartId, request);
    }
}
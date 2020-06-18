package com.example.FashionStore.Services;

import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Models.Tag;
import com.example.FashionStore.Models.User;
import com.example.FashionStore.Repositories.CartRepository;
import com.example.FashionStore.Repositories.UserRepository;
import com.example.FashionStore.Response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CartService {
    private CartRepository cartRepository;
    private UserRepository userRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> addNewCartItem(Integer userId, Cart newCartItem) {
        User user = userRepository.findById(userId).get();
        if (cartRepository.existsByProduct(newCartItem.getProduct().getProductName())) {
            Cart cart = cartRepository.findByProduct(newCartItem.getProduct().getProductName());
            cart.setQuantity(cart.getQuantity() + 1);
            cart.setUser(user);
            cartRepository.save(cart);
            return ResponseEntity.ok().body("");
        } else {
            Cart cart = new Cart();
            newCartItem.getProduct();
            newCartItem.getQuantity();
            newCartItem.setUser(user);
            newCartItem.getSize();
            cartRepository.save(cart);
            return ResponseEntity.ok().body("");
        }
    }


    public void deleteCartItem(Integer id) {
        if (cartRepository.existsById(id)) {
            Cart cart = cartRepository.findById(id).get();
            cartRepository.delete(cart);
        }
    }

    public ResponseEntity<?> updateCartItem(Integer cartId, Cart updateCartItem) {
        if (cartRepository.existsById(cartId)) {
            Cart cart = cartRepository.findById(cartId).get();
            cart.setQuantity(updateCartItem.getQuantity());
            cartRepository.save(cart);
            return ResponseEntity.ok().body("");
        }
        return ResponseEntity.ok().body("");
    }

    public List<Cart> getAllCartItems(Integer userId) {
        List<Cart> cartList = cartRepository.findByUserUserId(userId);
        return cartList;
    }
//    public Cart getCartByCartId(Integer cartId) {
//        Cart cart = cartRepository.findById(cartId).get();
//        return cart;
//    }
    public ResponseEntity<?> getCartItemByProductName(String productName) {
        if (!cartRepository.existsByProduct(productName)) {
            return ResponseEntity.ok().body(new MessageResponse("Tag not available!!!"));
        } else {
            Cart cart = cartRepository.findByProduct(productName);
            return ResponseEntity.ok().body(cart);
        }
    }

    // get equipment by id
    public ResponseEntity<?> getCartItemProductById(Integer id) {
        if (!cartRepository.existsById(id)) {
            return ResponseEntity.ok().body(new MessageResponse("Product not available!!!"));
        } else {
            Cart cart = cartRepository.findById(id).get();
            return ResponseEntity.ok().body(cart);
        }
    }
}

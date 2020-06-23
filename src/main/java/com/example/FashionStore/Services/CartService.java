package com.example.FashionStore.Services;

import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.Tag;
import com.example.FashionStore.Models.User;
import com.example.FashionStore.Repositories.CartRepository;
import com.example.FashionStore.Repositories.ProductRepository;
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
    private ProductRepository productRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> addNewCartItem(Integer productId,Integer quantity, String size, Double total,HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        Product product = productRepository.findById(productId).get();
            if (cartRepository.existsByProductAndSize(product,size)) {
            Cart cart = cartRepository.findByProductAndSize(product,size);
            cart.setQuantity(cart.getQuantity() + quantity);
            cart.setTotal(total);
            cartRepository.save(cart);
            return ResponseEntity.ok().body("Added to the existing product");
        } else {
            Cart cart = new Cart();
            cart.setProduct(product);
            cart.setUser(user);
            cart.setQuantity(quantity);
            cart.setTotal(total);
            cart.setSize(size);
            cartRepository.save(cart);
            return ResponseEntity.ok().body("");
        }
    }


    public List<Cart> deleteCartProductByProductId(Integer productId,String size,HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        System.out.println(productId+" = "+user.getUserId()+" = "+size);
        if (cartRepository.existsByProductProductIdAndSizeAndUserUserId(productId, size,user.getUserId())) {
            Cart cart = cartRepository.findByProductProductIdAndSizeAndUserUserId(productId,size,user.getUserId());
            cartRepository.delete(cart);
            return getAllCartItems(request);
        }else {
            System.out.println("not going check this");
            return null;
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

    public List<Cart> getAllCartItems(HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        List<Cart> cartList = cartRepository.findByUser(user);
        return cartList;
    }
//    public Cart getCartByCartId(Integer cartId) {
//        Cart cart = cartRepository.findById(cartId).get();
//        return cart;
//    }
//    public ResponseEntity<?> getCartItemByProductName(String productName) {
//        if (!cartRepository.existsByProduct(productName)) {
//            return ResponseEntity.ok().body(new MessageResponse("Tag not available!!!"));
//        } else {
//            Cart cart = cartRepository.findByProduct(productName);
//            return ResponseEntity.ok().body(cart);
//        }
//    }

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

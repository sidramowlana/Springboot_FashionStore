package com.example.FashionStore.Services;

import com.example.FashionStore.Models.*;
import com.example.FashionStore.Repositories.*;
import com.example.FashionStore.Response.MessageResponse;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {
    private OrdersRepository ordersRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private CartRepository cartRepository;

    private CartOrdersRepository cartOrdersRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository, CartOrdersRepository cartOrdersRepository) {
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartOrdersRepository = cartOrdersRepository;
    }

    public List<Orders> getAllUserOrders(Integer userId, String status, HttpServletRequest request) {
        User user = userRepository.findById(userId).get();
        List<Orders> ordersList = ordersRepository.findByUserAndStatus(user, status);
        return ordersList;
    }

    public List<CartOrders> getAllCartByOrderId(Integer ordersId) {
        Orders orders = ordersRepository.findById(ordersId).get();
        List<CartOrders> cartList = cartOrdersRepository.findByOrders(orders);
        return cartList;
    }

    public ResponseEntity<Orders> addOrder(Orders newOorder, HttpServletRequest request) {
        String userName = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(userName).get();
        Orders orders = new Orders();
        orders.setUser(user);
        orders.setTotal(newOorder.getTotal());
        orders.setStatus(newOorder.getStatus());
        orders.setDate(newOorder.getDate());
        ordersRepository.save(orders);
        return ResponseEntity.ok().body(orders);
    }

    public ResponseEntity<MessageResponse> addCartOrders(CartOrders newCartOrders, HttpServletRequest request) {
        CartOrders cartOrders = new CartOrders();
        cartOrders.setOrders(newCartOrders.getOrders());
        cartOrders.setCart(newCartOrders.getCart());
        Product product = newCartOrders.getCart().getProduct();
        System.out.println(newCartOrders.getCart().getProduct().getQuantity() - newCartOrders.getCart().getQuantity());
        product.setQuantity(newCartOrders.getCart().getProduct().getQuantity() - newCartOrders.getCart().getQuantity());
        productRepository.save(product);
        Cart cart = newCartOrders.getCart();
        cart.setPurchased(true);
        cartRepository.save(cart);
        cartOrdersRepository.save(cartOrders);
        return ResponseEntity.ok().body(new MessageResponse("Your orders have been made successfully"));
    }

    public ResponseEntity<CartOrders> updateOrderStatus(Integer cartOrdersId, CartOrders updateCartOrders) {
        if (cartOrdersRepository.existsById(cartOrdersId)) {
            CartOrders cartOrders = cartOrdersRepository.findById(cartOrdersId).get();
            Product product = new Product();
            cartOrders.getOrders().setDate(updateCartOrders.getOrders().getDate());
            cartOrders.getOrders().setStatus(updateCartOrders.getOrders().getStatus());
            product.setQuantity(product.getQuantity() + updateCartOrders.getCart().getQuantity());
            productRepository.save(product);
            cartOrdersRepository.save(cartOrders);
            return ResponseEntity.ok().body(cartOrders);
        }
        return null;
    }
}
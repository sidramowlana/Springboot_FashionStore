package com.example.FashionStore.Services;

import com.example.FashionStore.Models.CartOrders;
import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Models.Orders;
import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.User;
import com.example.FashionStore.Repositories.*;
import com.example.FashionStore.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        List<Orders> ordersList = ordersRepository.findByUserAndStatusOrderByOrdersIdDesc(user, status);
        return ordersList;
    }

    public List<Orders> getAllPendingOrdersByStatus(String status) {
        List<Orders> ordersList = ordersRepository.findByStatus(status);
        return ordersList;
    }

    public List<CartOrders> getAllCartByOrderId(Integer ordersId) {
        Orders orders = ordersRepository.findById(ordersId).get();
        List<CartOrders> cartList = cartOrdersRepository.findByOrders(orders);
        return cartList;
    }

    public Orders getAOrderById(Integer orderId) {
        Orders orders = ordersRepository.findById(orderId).get();
        return orders;
    }

    public List<CartOrders> getAllCartOrdersByUserId(Integer userId, HttpServletRequest request) {
        List<CartOrders> cartOrdersList = cartOrdersRepository.findByOrdersUserUserIdOrderByCardOrderId(userId);
        return cartOrdersList;
    }

    public ResponseEntity<Orders> addOrder(Orders newOrder, HttpServletRequest request) {
        String userName = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(userName).get();
        Orders orders = new Orders();
        orders.setUser(user);
        orders.setTotal(newOrder.getTotal());
        orders.setStatus(newOrder.getStatus());
        orders.setDate(newOrder.getDate());
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
            Product product = cartOrders.getCart().getProduct();
            cartOrders.getOrders().setDate(updateCartOrders.getOrders().getDate());
            cartOrders.getOrders().setStatus(updateCartOrders.getOrders().getStatus());
            product.setQuantity(product.getQuantity() + updateCartOrders.getCart().getQuantity());
            productRepository.save(product);
            cartOrdersRepository.save(cartOrders);
            return ResponseEntity.ok().body(cartOrders);
        }
        return null;
    }

    public ResponseEntity<?> updateOrderStatusByOrderId(Integer ordersId, String status) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = sdf.format(new Date());

        if (ordersRepository.existsById(ordersId)) {
            Orders orders = ordersRepository.findById(ordersId).get();
            orders.setStatus(status);
            orders.setDate(date);
            List<CartOrders> cartOrdersList = cartOrdersRepository.findByOrders(orders);
            for (CartOrders cartOrders : cartOrdersList) {
                Product product = cartOrders.getCart().getProduct();
                product.setQuantity(product.getQuantity() + cartOrders.getCart().getQuantity());
                productRepository.save(product);
                ordersRepository.save(orders);
            }
            return ResponseEntity.ok().body(orders);
        }
        return ResponseEntity.ok().body(new MessageResponse("No order id availabel"));
    }
}
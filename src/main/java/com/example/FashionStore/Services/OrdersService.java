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

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository) {
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public List<Orders> getAllUserOrders(Integer userId, String status, HttpServletRequest request) {
        User user = userRepository.findById(userId).get();
        List<Orders> ordersList = ordersRepository.findByUserAndStatus(user, status);
        return ordersList;
    }
    public List<Cart> getAllCartByOrderId(Integer ordersId,HttpServletRequest request){
        String userName = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(userName).get();
        if(cartRepository.existsByUser(user)){
            List<Cart> cartList = null;
            return cartList;
        }
        else{
            return null;
        }

    }
//    public ResponseEntity<MessageResponse> addUserCartOrders(CartOrders newCartOrders, HttpServletRequest request) {
//        String userName = request.getUserPrincipal().getName();
//        User user = userRepository.findByUsername(userName).get();
//        Orders orders = new Orders();
//        CartOrders cartOrders = new CartOrders();
//        Cart cart = newCartOrders.getCart();
//        System.out.println("gggggggggggggggggggg");
//        List<Cart> cartList = new ArrayList<>();
//        cartList.add(cart);
//        System.out.println("ddd: " + cartList);
//        orders.setDate(newCartOrders.getOrders().getDate());
//        orders.setStatus(newCartOrders.getOrders().getStatus());
//        orders.setTotal(newCartOrders.getOrders().getTotal());
//        orders.setUser(user);
//        ordersRepository.save(orders); //save the users order
//        for (Cart c : cartList) {
//            Product product = c.getProduct();
//            product.setQuantity(product.getQuantity() - c.getQuantity());
//            productRepository.save(product);
//            c.setPurchased(true);
//            cartRepository.save(c);
//            cartOrders.setCart(newCartOrders.getCart());
//            cartOrders.setOrders(orders);
//            System.out.println(cartOrders.getCart());
//            System.out.println(cartOrders.getOrders());
//            cartOrdersRepository.save(cartOrders);// save the cart and the order in cartOrder
//        }
//        System.out.println("They luffy");
//        return ResponseEntity.ok().body(new MessageResponse("Your orders have been made successfully"));
//    }
    public ResponseEntity<MessageResponse> addUserCartOrders(Orders newCartOrders, HttpServletRequest request) {
        String userName = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(userName).get();
        List<Cart> cartList = newCartOrders.getCartList();
        System.out.println("cartlist: "+cartList);
        Orders orders = new Orders();
        orders.setDate(newCartOrders.getDate());
        orders.setStatus(newCartOrders.getStatus());
        orders.setUser(user);
        orders.setCartList(cartList);
        orders.setTotal(newCartOrders.getTotal());
        ordersRepository.save(orders);
        for (Cart cart : cartList) {
            cart.setPurchased(true);
            cartRepository.save(cart);
        }
        return ResponseEntity.ok().body(new MessageResponse("Your orders have been made successfully"));
    }

}

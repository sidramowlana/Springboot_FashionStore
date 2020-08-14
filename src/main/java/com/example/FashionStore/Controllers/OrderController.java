package com.example.FashionStore.Controllers;

import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Models.CartOrders;
import com.example.FashionStore.Models.Orders;
import com.example.FashionStore.Response.MessageResponse;
import com.example.FashionStore.Services.AuthService;
import com.example.FashionStore.Services.OrdersService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/orders")
@RestController
public class OrderController {

    private OrdersService ordersService;

    @Autowired
    public OrderController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/cart/add-order")
    public ResponseEntity<MessageResponse> addCartOrders(@RequestBody CartOrders newOrders, HttpServletRequest request) {
        return ordersService.addCartOrders(newOrders, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/orders/add-order")
    public ResponseEntity<Orders> addOrder(@RequestBody Orders orders, HttpServletRequest request) {
        return ordersService.addOrder(orders, request);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/status/{orderStatus}/user/{userId}")
    public List<Orders> getAllUserOrdersByStatus(@PathVariable Integer userId, @PathVariable String orderStatus, HttpServletRequest request) {
        return ordersService.getAllUserOrders(userId, orderStatus, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/cart/{ordersId}")
    public List<CartOrders> getAllCartByOrderId(@PathVariable Integer ordersId, HttpServletRequest request) {
        return ordersService.getAllCartByOrderId(ordersId);
    }

    //chec in android if not delete
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping(value = "/order-update/{cartOrdersId}")
    public ResponseEntity<CartOrders> updateOrderStatus(@PathVariable Integer cartOrdersId, @RequestBody CartOrders updateCartOrders, HttpServletRequest request) {
        return ordersService.updateOrderStatus(cartOrdersId, updateCartOrders);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping(value = "/order-status/{ordersId}/{status}")
    public ResponseEntity<?> updateOrderStatusByOrderId(@PathVariable Integer ordersId, @PathVariable String status) {
        return ordersService.updateOrderStatusByOrderId(ordersId, status);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/cart-orders/{userId}")
    public List<CartOrders> getAllCartOrdersByUserId(@PathVariable Integer userId, HttpServletRequest request) {
        return ordersService.getAllCartOrdersByUserId(userId,request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all/{status}")
    public List<Orders> getAllPendingOrdersByStatus(@PathVariable String status, HttpServletRequest request) {
        return ordersService.getAllPendingOrdersByStatus(status);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/order/{orderId}")
    public Orders getAOrderById(@PathVariable Integer orderId)
    {
        return ordersService.getAOrderById(orderId);
    }
}

package com.example.FashionStore.Controllers;

import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Models.Orders;
import com.example.FashionStore.Response.MessageResponse;
import com.example.FashionStore.Services.AuthService;
import com.example.FashionStore.Services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("api/orders")
@RestController
public class OrderController {

    private OrdersService ordersService;

    @Autowired
    public OrderController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/add-order")
    public ResponseEntity<MessageResponse> addUserOrders(@RequestBody Orders newOrders, HttpServletRequest request) {
        System.out.println("cartorders: "+newOrders);
        return ordersService.addUserCartOrders(newOrders,request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/status/{orderStatus}/user/{userId}")
    public List<Orders> getAllUserOrders(@PathVariable Integer userId,@PathVariable String orderStatus, HttpServletRequest request){
        return ordersService.getAllUserOrders(userId,orderStatus,request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/cart/{ordersId}")
    public List<Cart> getAllCartByOrderId(@PathVariable Integer ordersId, HttpServletRequest request){
        return ordersService.getAllCartByOrderId(ordersId,request);
    }

}

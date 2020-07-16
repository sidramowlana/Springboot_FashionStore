//package com.example.FashionStore.Models;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//public class CartOrders {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer cardOrderId;
//
//    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    @JoinColumn(name = "cart", referencedColumnName = "cartId")
//    private Cart cart;
////
////    @Transient
////    @JsonIgnore
////    private List<Cart> cartList;
//
//    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    @JoinColumn(name = "orders", referencedColumnName = "ordersId")
//    private Orders orders;
//
//
////
////    public CartOrders(Cart cart, Orders orders) {
////        this.cart = cart;
////        this.orders = orders;
////    }
//
////    public CartOrders(List<Cart> cartList, Orders orders) {
////        this.cartList = cartList;
////        this.orders = orders;
////    }
//}

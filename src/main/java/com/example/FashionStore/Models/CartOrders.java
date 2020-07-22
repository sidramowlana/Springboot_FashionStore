package com.example.FashionStore.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardOrderId;

    @ManyToOne(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn(name = "cart", referencedColumnName = "cartId")
    private Cart cart;

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "orders", referencedColumnName = "ordersId")
    private Orders orders;

}
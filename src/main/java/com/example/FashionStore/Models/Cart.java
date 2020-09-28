package com.example.FashionStore.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "cart")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user")
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "product", referencedColumnName = "productId")
    private Product product;

    private int quantity;
    private String size;
    private double total;
    private boolean isPurchased;

    public Cart(User user, Product product, int quantity, String size, double total, boolean isPurchased) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.size = size;
        this.total = total;
        this.isPurchased = isPurchased;
    }
}

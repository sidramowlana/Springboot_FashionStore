package com.example.FashionStore.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rateReviewId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user", referencedColumnName = "userId")
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "product", referencedColumnName = "productId")
    private Product product;
    private int rate = 0;
    private String feedback;
    private String date;

    public RateReview(User user, Product product, int rate, String feedback, String date) {
        this.user = user;
        this.product = product;
        this.rate = rate;
        this.feedback = feedback;
        this.date = date;
    }
}

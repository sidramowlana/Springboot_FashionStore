package com.example.FashionStore.Models;

import java.util.Date;

public class RateReview {
    private User user;
    private Product product;
    private int rate = 0;
    private String feedback;
    private Date date;

    public RateReview() {
    }

    public RateReview(User user, Product product, int rate, String feedback, Date date) {
        this.user = user;
        this.product = product;
        this.rate = rate;
        this.feedback = feedback;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

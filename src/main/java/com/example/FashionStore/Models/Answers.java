package com.example.FashionStore.Models;


import java.util.Date;

public class Answers{
    private User user;
    private Product product;
    private ProductInquiry productInquiry;
    private Date date = new Date();
    private String answer;

    public Answers() {
    }

    public Answers(User user, Product product, ProductInquiry productInquiry, Date date, String answer) {
        this.user = user;
        this.product = product;
        this.productInquiry = productInquiry;
        this.date = date;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public ProductInquiry getProductInquiry() {
        return productInquiry;
    }

    public void setProductInquiry(ProductInquiry productInquiry) {
        this.productInquiry = productInquiry;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

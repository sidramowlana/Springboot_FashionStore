package com.example.FashionStore.Models;

import java.util.Date;

public class ProductInquiry  {
    private User user;
    private String question;
    private Product product;
    private Date date = new Date();
//    private Answers answers;

    public ProductInquiry() {
    }

    public ProductInquiry(User user, String question, Product product, Date date) {
        this.user = user;
        this.question = question;
        this.product = product;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

//    public Answers getAnswers() {
//        return answers;
//    }
//
//    public void setAnswers(Answers answers) {
//        this.answers = answers;
//    }
}

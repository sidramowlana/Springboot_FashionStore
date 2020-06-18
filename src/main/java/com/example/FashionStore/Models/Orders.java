package com.example.FashionStore.Models;

public class Orders  {
    private String date;
    private String status;
    private User user;
    private double total;

    public Orders() {
    }

//    public Orders(User user) {
//        this.user = user;
//        this.orderStatus = "Pending";
//    }


    public Orders(String date, String status, User user, double total) {
        this.date = date;
        this.status = status;
        this.user = user;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
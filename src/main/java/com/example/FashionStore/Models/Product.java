package com.example.FashionStore.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String productName;
    private String shortDescription;
    private String category;
    private double price;
    private int quantity;
    private String scaledImage;

//    @JsonIgnore
    @Transient
    String[] catergoryArray;

    public Product(String productName, String shortDescription, String category, double price, int quantity, String scaledImage) {
        this.productName = productName;
        this.shortDescription = shortDescription;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.scaledImage = scaledImage;
    }
    public Product(String productName, String shortDescription, double price, int quantity, String scaledImage, String[] catergoryArray) {
        this.productName = productName;
        this.shortDescription = shortDescription;
        this.price = price;
        this.quantity = quantity;
        this.scaledImage = scaledImage;
        this.catergoryArray = catergoryArray;
    }
}

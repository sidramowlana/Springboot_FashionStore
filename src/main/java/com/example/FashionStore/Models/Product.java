package com.example.FashionStore.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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
    private String longDescription;
    private String category;
    private double price;
    private int quantity;
    private String scaledImage;
    private String fullImage;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "product", referencedColumnName = "productId")
    private List<ProductTag> productTag;

//    @Transient
//    @Json
//    private List<Tag> tagsList;;

    public Product(String productName, String shortDescription, String longDescription, String category, double price, int quantity, String scaledImage, String fullImage, List<ProductTag> productTag) {
        this.productName = productName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.scaledImage = scaledImage;
        this.fullImage = fullImage;
        this.productTag = productTag;
    }
}

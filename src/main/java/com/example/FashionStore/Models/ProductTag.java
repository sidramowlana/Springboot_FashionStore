package com.example.FashionStore.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "productTag")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productTagId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "product", referencedColumnName = "productId")
    @JsonIgnore
    private Product product;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "tag", referencedColumnName = "tagId")
    private Tag tag;
}

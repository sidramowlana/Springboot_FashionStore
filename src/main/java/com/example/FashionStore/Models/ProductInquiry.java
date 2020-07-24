package com.example.FashionStore.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductInquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ProductInquiryId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user", referencedColumnName = "userId")
    private User user;
    private String question;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "product", referencedColumnName = "productId")
    private Product product;
    private String date;
    private String answers;
    private boolean isReplied;

}
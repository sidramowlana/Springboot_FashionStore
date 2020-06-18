package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductName(String productName);

    boolean existsByProductName(String productName);
}

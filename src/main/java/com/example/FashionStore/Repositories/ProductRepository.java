package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductName(String productName);

    List<Product> findByCategory(String tagName);

    boolean existsByProductName(String productName);
}

package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.ProductInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInquiryRepository extends JpaRepository<ProductInquiry, Integer> {
    List<ProductInquiry> findByProductProductId(Integer productId);

}

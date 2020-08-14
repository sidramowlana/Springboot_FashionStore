package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, Integer> {

    List<ProductTag> findByTagTagId(Integer tagId);
    boolean existsByTagTagId(Integer id);
}

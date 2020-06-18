package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByTag(String tagName);

    boolean existsByTag(String tagName);

}

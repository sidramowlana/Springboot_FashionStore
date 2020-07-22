package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    boolean existsByTag(String tagName);

    Tag findByTag(String tagName);

}

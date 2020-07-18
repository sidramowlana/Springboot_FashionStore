package com.example.FashionStore.Repositories;

import com.example.FashionStore.Models.RateReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateReviewRepository extends JpaRepository<RateReview, Integer> {
}

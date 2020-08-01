package com.example.FashionStore.Services;

import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.RateReview;
import com.example.FashionStore.Models.User;
import com.example.FashionStore.Repositories.*;
import com.example.FashionStore.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class RateReviewService {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private RateReviewRepository rateReviewRepository;

    @Autowired
    public RateReviewService(RateReviewRepository rateReviewRepository, UserRepository userRepository,ProductRepository productRepository) {
        this.rateReviewRepository = rateReviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<MessageResponse> onAddRateReviewByProductId(Integer productId, RateReview newRateReview, HttpServletRequest request) {
        if (productRepository.existsById(productId)) {
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
            Product product = productRepository.findById(productId).get();
            RateReview rateReview = new RateReview();
            rateReview.setDate(newRateReview.getDate());
            rateReview.setProduct(product);
            rateReview.setFeedback(newRateReview.getFeedback());
            rateReview.setRate(newRateReview.getRate());
            rateReview.setUser(user);
            rateReviewRepository.save(rateReview);
            return ResponseEntity.ok().body(new MessageResponse("Thank you for your feedback"));
        } else {
            return ResponseEntity.ok().body(new MessageResponse("Product Not Available"));
        }
    }
    public List<RateReview> getRateReviewByProductId(Integer productId, HttpServletRequest request){
        List<RateReview> rateReviewsList = rateReviewRepository.findByProductProductId(productId);
        return rateReviewsList;
    }

    public RateReview getRateReviewById(Integer rateReviewId){
        RateReview rateReview = rateReviewRepository.findById(rateReviewId).get();
        return rateReview;
    }
}
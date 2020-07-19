package com.example.FashionStore.Controllers;

import com.example.FashionStore.Models.RateReview;
import com.example.FashionStore.Response.MessageResponse;
import com.example.FashionStore.Services.CartService;
import com.example.FashionStore.Services.RateReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("api/rate")
@RestController
public class RateReviewController {

    private RateReviewService rateReviewService;

    public RateReviewController(RateReviewService rateReviewService) {
        this.rateReviewService = rateReviewService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/product-rate/{productId}")
    public ResponseEntity<MessageResponse> onAddRateReviewByProductId(@PathVariable Integer productId, @RequestBody RateReview newRateReview, HttpServletRequest request) {
        return rateReviewService.onAddRateReviewByProductId(productId, newRateReview, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/product-all/{productId}")
    public List<RateReview> getRateReviewByProductId(@PathVariable Integer productId, HttpServletRequest request) {
        System.out.println("lets see: "+productId);
        return rateReviewService.getRateReviewByProductId(productId, request);
    }
}

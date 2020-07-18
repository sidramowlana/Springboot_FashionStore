package com.example.FashionStore.Controllers;

import com.example.FashionStore.Models.RateReview;
import com.example.FashionStore.Response.MessageResponse;
import com.example.FashionStore.Services.CartService;
import com.example.FashionStore.Services.RateReviewService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/product-rate/{productId}")
    public ResponseEntity<MessageResponse> onAddRateReviewByProductId(@PathVariable Integer productId, @RequestBody RateReview newRateReview, HttpServletRequest request) {
        return rateReviewService.onAddRateReviewByProductId(productId, newRateReview,request);
    }

//    @GET("api/rate/all/{productId}")
//    Call<List<RateReview>> getAllProductRateByProductId(@Path("productId") Integer productId, @Header("Authorization") String token);

}

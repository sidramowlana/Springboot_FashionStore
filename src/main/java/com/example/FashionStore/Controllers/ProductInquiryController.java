package com.example.FashionStore.Controllers;

import com.example.FashionStore.Models.ProductInquiry;
import com.example.FashionStore.Response.MessageResponse;
import com.example.FashionStore.Services.ProductInquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")

@RequestMapping("api/inquiry")
@RestController
public class ProductInquiryController {
    private ProductInquiryService productInquiryService;

    public ProductInquiryController(ProductInquiryService productInquiryService) {
        this.productInquiryService = productInquiryService;
    }

    //user asking for question
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/product/{productId}")
    public List<ProductInquiry> onAddProductInquiryByProductId(@PathVariable Integer productId, @RequestBody ProductInquiry productInquiry, HttpServletRequest request) {
        return productInquiryService.onAddProductInquiryByProductId(productId, productInquiry, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/product-all/{productId}")
    public List<ProductInquiry> onGetAllProductInquiryByProductId(@PathVariable Integer productId, HttpServletRequest request) {
        System.out.println("here: "+productInquiryService.onGetAllProductInquiryByProductId(productId));
        return productInquiryService.onGetAllProductInquiryByProductId(productId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/answered/{isAnswered}")
    public List<ProductInquiry> onGetAllProductInquiryAnswered(@PathVariable boolean isAnswered, HttpServletRequest request) {
        return productInquiryService.onGetAllProductInquiryAnswered(isAnswered);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/product/{inquiryId}")
    public ProductInquiry onGetInquiryById(@PathVariable Integer inquiryId, HttpServletRequest request) {
        return productInquiryService.onGetInquiryById(inquiryId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/product-answer/{productInquiryId}")
    public ResponseEntity<MessageResponse> onAddAnswerByProductInquiryId(@PathVariable Integer productInquiryId,
                                                                         @RequestBody ProductInquiry productInquiry, HttpServletRequest request) {
        return productInquiryService.onAddAnswerByProductInquiryId(productInquiryId,productInquiry);
    }

}

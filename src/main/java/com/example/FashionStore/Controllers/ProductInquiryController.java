package com.example.FashionStore.Controllers;

import com.example.FashionStore.Models.ProductInquiry;
import com.example.FashionStore.Services.ProductInquiryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("api/inquiry")
@RestController
public class ProductInquiryController {
    private ProductInquiryService productInquiryService;

    public ProductInquiryController(ProductInquiryService productInquiryService) {
        this.productInquiryService = productInquiryService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/product/{productId}")
    public List<ProductInquiry> onAddProductInquiryByProductId(@PathVariable Integer productId, @RequestBody ProductInquiry productInquiry, HttpServletRequest request) {
        return productInquiryService.onAddProductInquiryByProductId(productId, productInquiry, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/product-all/{productId}")
    public List<ProductInquiry> onGetAllProductInquiry(@PathVariable Integer productId, HttpServletRequest request) {
        return productInquiryService.onGetAllProductInquiry(productId);
    }
}
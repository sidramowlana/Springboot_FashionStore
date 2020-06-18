package com.example.FashionStore.Controllers;

import com.example.FashionStore.Models.Cart;
import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Repositories.ProductRepository;
import com.example.FashionStore.Services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/products")
@RestController
public class ProductController {
    private ProductRepository productRepository;
    private ProductService productService;

    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    //users
    @GetMapping(value = "/productAll")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/productAll/{productId}")
    public ResponseEntity<?> getProductByProductId(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    //admin
//    add product
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/createProduct")
    public ResponseEntity<?> addCart(@RequestBody Product newProduct) {
        return productService.addNewProduct(newProduct);
    }

    //    delete product
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/deleteProduct/{productId}")
    public void deleteProduct(@PathVariable Integer productId) {
        productService.deleteProductByProductId(productId);
    }

    //    update product
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/updateProduct/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer productId, @RequestBody Product updateProduct) {
        return productService.updateProductByProductId(productId, updateProduct);
    }
}

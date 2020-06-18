package com.example.FashionStore.Services;

import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Repositories.ProductRepository;
import com.example.FashionStore.Response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //add new Product
    public ResponseEntity<?> addNewProduct(Product newProduct) {
        if (productRepository.existsByProductName(newProduct.getProductName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Product already exist!!!"));
        }
        Product product = new Product(
                newProduct.getProductName(),
                newProduct.getShortDescription(),
                newProduct.getLongDescription(),
                newProduct.getCategory(),
                newProduct.getPrice(),
                newProduct.getQuantity(),
                newProduct.getScaledImage(),
                newProduct.getFullImage(),
                newProduct.getProductTag()
        );
        productRepository.save(product);
        return ResponseEntity.ok().body(new MessageResponse("Successfully Product Added"));
    }

    //delete a Product
    public void deleteProductByProductId(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
    }

    //get all Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //get a Product by name
    public ResponseEntity<?> getProductByName(String productName) {
        if (!productRepository.existsByProductName(productName)) {
            return ResponseEntity.ok().body(new MessageResponse("Product not available!!!"));
        } else {
            Product product = productRepository.findByProductName(productName);
            return ResponseEntity.ok().body(product);
        }
    }

    // get product by id
    public ResponseEntity<?> getProductById(Integer id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.ok().body(new MessageResponse("Product not available!!!"));
        } else {
            Product product = productRepository.findById(id).get();
            return ResponseEntity.ok().body(product);
        }
    }

    public ResponseEntity<?> updateProductByProductId(Integer productId, Product updateProduct) {
        if (productRepository.existsById(productId)) {
            Product product = productRepository.findById(productId).get();
            product.setProductName(updateProduct.getProductName());
            product.setShortDescription(updateProduct.getShortDescription());
            product.setLongDescription(updateProduct.getLongDescription());
            product.setCategory(updateProduct.getCategory());
            product.setPrice(updateProduct.getPrice());
            product.setQuantity(updateProduct.getQuantity());
            product.setScaledImage(updateProduct.getScaledImage());
            product.setFullImage(updateProduct.getFullImage());
            productRepository.save(product);
            return ResponseEntity.ok().body(product);
        } else {
            return ResponseEntity.ok().body("Product not available");
        }
    }
}

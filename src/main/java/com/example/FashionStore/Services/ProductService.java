package com.example.FashionStore.Services;

import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.ProductTag;
import com.example.FashionStore.Models.Tag;
import com.example.FashionStore.Repositories.ProductRepository;
import com.example.FashionStore.Repositories.ProductTagRepository;
import com.example.FashionStore.Repositories.TagRepository;
import com.example.FashionStore.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private TagRepository tagRepository;
    private ProductTagRepository productTagRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, TagRepository tagRepository, ProductTagRepository productTagRepository) {
        this.productRepository = productRepository;
        this.tagRepository = tagRepository;
        this.productTagRepository = productTagRepository;
    }

    //add new Product
    public ResponseEntity<MessageResponse> addProduct(Product newProduct) {
        if (productRepository.existsByProductName(newProduct.getProductName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Product already exist!!!"));
        }
        Product product = new Product();
        product.setProductName(newProduct.getProductName());
        product.setShortDescription(newProduct.getShortDescription());
        product.setPrice(newProduct.getPrice());
        product.setQuantity(newProduct.getQuantity());
        product.setScaledImage(newProduct.getScaledImage());
        String catergoryArray[] = newProduct.getCatergoryArray();
        System.out.println(Arrays.toString(newProduct.getCatergoryArray()));
        System.out.println(catergoryArray);
        for (int j = 0; j < catergoryArray.length; j++) {
            if (catergoryArray[j].equals("Men") || catergoryArray[j].equals("Women") || catergoryArray[j].equals("Kids")) {
                product.setCategory(catergoryArray[j]);
            }
        }
        productRepository.save(product);

        for (int i = 0; i < catergoryArray.length; i++) {
            Tag tag = tagRepository.findByTag(catergoryArray[i]);
            ProductTag productTag = new ProductTag();
            productTag.setProduct(product);
            productTag.setTag(tag);
            productTagRepository.save(productTag);
        }
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
            System.out.println(product);
//            if(updateProduct.getScaledImage().isEmpty()) {
//                product.setProductName(updateProduct.getProductName());
//                product.setShortDescription(updateProduct.getShortDescription());
//                product.setPrice(updateProduct.getPrice());
//                product.setQuantity(updateProduct.getQuantity());
//                productRepository.save(product);
//            }
//            else{
//                product.setScaledImage(updateProduct.getScaledImage());
                product.setProductName(updateProduct.getProductName());
                product.setShortDescription(updateProduct.getShortDescription());
                product.setPrice(updateProduct.getPrice());
                product.setQuantity(updateProduct.getQuantity());
                productRepository.save(product);
//            }
            return ResponseEntity.ok().body(product);
        } else {
            return ResponseEntity.ok().body("Product not available");
        }
    }

    public List<Product> getAllProductByTagName(String tagName) {
        List<Product> productList = productRepository.findByCategory(tagName);
        System.out.println("productList: " + productList);
        return productList;
    }
}

package com.example.FashionStore.Services;

import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.ProductInquiry;
import com.example.FashionStore.Models.User;
import com.example.FashionStore.Repositories.ProductInquiryRepository;
import com.example.FashionStore.Repositories.ProductRepository;
import com.example.FashionStore.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ProductInquiryService {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ProductInquiryRepository productInquiryRepository;

    @Autowired
    public ProductInquiryService(ProductInquiryRepository productInquiryRepository, UserRepository userRepository,ProductRepository productRepository) {
        this.productInquiryRepository = productInquiryRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<ProductInquiry> onAddProductInquiryByProductId(Integer productId, ProductInquiry newProductInquiry, HttpServletRequest request){
        Product product = productRepository.findById(productId).get();
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        ProductInquiry productInquiry = new ProductInquiry();
        productInquiry.setDate(newProductInquiry.getDate());
        productInquiry.setProduct(product);
        productInquiry.setUser(user);
        productInquiry.setQuestion(newProductInquiry.getQuestion());
        productInquiryRepository.save(productInquiry);
        return onGetAllProductInquiry(productId);
    }

    public List<ProductInquiry> onGetAllProductInquiry(Integer productId){
        List<ProductInquiry> productInquiryList = productInquiryRepository.findByProductProductId(productId);
        return productInquiryList;
    }

}

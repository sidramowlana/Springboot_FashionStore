package com.example.FashionStore.Services;

import com.example.FashionStore.Models.Product;
import com.example.FashionStore.Models.ProductInquiry;
import com.example.FashionStore.Models.User;
import com.example.FashionStore.Repositories.ProductInquiryRepository;
import com.example.FashionStore.Repositories.ProductRepository;
import com.example.FashionStore.Repositories.UserRepository;
import com.example.FashionStore.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ProductInquiryService(ProductInquiryRepository productInquiryRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.productInquiryRepository = productInquiryRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<ProductInquiry> onAddProductInquiryByProductId(Integer productId, ProductInquiry newProductInquiry, HttpServletRequest request) {
        Product product = productRepository.findById(productId).get();
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        ProductInquiry productInquiry = new ProductInquiry();
        productInquiry.setDate(newProductInquiry.getDate());
        productInquiry.setReplied(false);
        productInquiry.setProduct(product);
        productInquiry.setUser(user);
        productInquiry.setQuestion(newProductInquiry.getQuestion());
        productInquiryRepository.save(productInquiry);
        List<ProductInquiry> productInquiryList = productInquiryRepository.findByProductProductId(productId);
        return productInquiryList;
    }

    public List<ProductInquiry> onGetAllProductInquiryByProductId(Integer productId) {
        List<ProductInquiry> productInquiryList = productInquiryRepository.findByProductProductId(productId);
        return productInquiryList;
    }

    public List<ProductInquiry> onGetAllProductInquiryAnswered(boolean isAnswered) {
        List<ProductInquiry> productInquiryList = productInquiryRepository.findByIsReplied(isAnswered);
        return productInquiryList;
    }

    public ResponseEntity<MessageResponse> onAddAnswerByProductInquiryId(Integer productInquiryId, ProductInquiry answerProductInquiry) {
        if(productInquiryRepository.existsById(productInquiryId)){
            ProductInquiry productInquiry = productInquiryRepository.findById(productInquiryId).get();
            productInquiry.setAnswers(answerProductInquiry.getAnswers());
            productInquiry.setReplied(true);
            productInquiryRepository.save(productInquiry);
            return ResponseEntity.ok().body(new MessageResponse("Successfully Replied to the inquiry"));
        }
        else
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Inquiry is not available Tag Added"));
        }
    }
}

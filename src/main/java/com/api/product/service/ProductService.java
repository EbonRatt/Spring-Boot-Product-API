package com.api.product.service;

import com.api.product.model.entities.Product;
import com.api.product.model.entities.User;
import com.api.product.model.request.ProductRequest;
import com.api.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> GetAllProducts(){

        return productRepository.findAll();
    }

    public Optional<Product> GetSingleProduct(int id){
        return productRepository.findById(id);
    }

    public Product AddProduct(ProductRequest product){
        Product temp = new Product();
        User user = new User();

        // When User Authentication
        Object ownerProduct = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(ownerProduct instanceof User){
            user = (User) ownerProduct;
        }

        if(CheckEmpty(product.getTitle()) && CheckEmpty(product.getColor().getName())) {
            temp.setTitle(product.getTitle());
            temp.setColor(product.getColor());
            temp.setUser(user);
            return productRepository.save(temp);

        }
        else{
            throw new RuntimeException("Please Fill Name or Color Name");
        }
    }

    public Product UpdateProductByName(ProductRequest product,String nameProduct){
        Product findProduct = productRepository.findByNameProduct(nameProduct);

        if(findProduct != null){
            if(CheckEmpty(product.getTitle())){
                findProduct.setTitle(product.getTitle());
               return productRepository.save(findProduct);
            }
            else{
                throw new RuntimeException("Please Fill Product The Name");
            }
        }
        else{
            throw new RuntimeException("Product Not Found!");
        }

    }

    public void DeleteProductByName(String nameProduct){
        Product findNameProduct = productRepository.findByNameProduct(nameProduct);
        if(findNameProduct != null){
            productRepository.delete(findNameProduct);
        }
        else{
            throw new RuntimeException("Not Found That Product");
        }
    }

    private boolean CheckEmpty(String productName){
        return productName != null && !productName.isEmpty();
    }

}

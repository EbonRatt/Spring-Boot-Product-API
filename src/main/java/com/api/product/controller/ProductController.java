package com.api.product.controller;

import com.api.product.model.entities.Product;
import com.api.product.model.reponse.ProductResponse;
import com.api.product.model.request.ProductRequest;
import com.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public ResponseEntity<ProductResponse<List<Product>>> GetAllProducts(){
        try{
            List<Product> productList = productService.GetAllProducts();
            return ResponseEntity.ok(ResponseBack("All Products",productList,HttpStatus.OK));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseBack("All Products Not Found!",null,HttpStatus.BAD_REQUEST));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse<Product>> GetSingleProduct(@PathVariable int id){
        try{
            Product product = productService.GetSingleProduct(id).orElseThrow();
            return ResponseEntity.ok(ResponseBack("Successful",product,HttpStatus.OK));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseBack("Product Not Found!",null,HttpStatus.NOT_FOUND));
        }
    }

    @PostMapping("/")
    public ResponseEntity<ProductResponse<Product>> AddProduct(@RequestBody ProductRequest productRequest){
        try{
            Product productAdd = productService.AddProduct(productRequest);
            return ResponseEntity.ok(ResponseBack("Add Successful",productAdd,HttpStatus.OK));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseBack(e.getMessage(),null,HttpStatus.BAD_REQUEST));
        }
    }

    @PutMapping("/{name}")
    public ResponseEntity<ProductResponse<Product>> UpdateProduct(@PathVariable String name,@RequestBody ProductRequest productRequest){
        try{
            Product tempProduct = productService.UpdateProductByName(productRequest,name);
            return ResponseEntity.ok(ResponseBack("Update Successful",tempProduct,HttpStatus.OK));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseBack(e.getMessage(),null,HttpStatus.BAD_REQUEST));
        }
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<ProductResponse<Product>> DeleteProduct(@PathVariable String name){
        try{
            productService.DeleteProductByName(name);
            return ResponseEntity.ok(ResponseBack("Deleted Successful",null,HttpStatus.OK));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseBack(e.getMessage(),null,HttpStatus.BAD_REQUEST));
        }
    }

    private <T> ProductResponse<T> ResponseBack(String message, T payload, HttpStatus httpStatus){
        ProductResponse<T> ProductResponse = new ProductResponse<>();
        ProductResponse.setMessage(message);
        ProductResponse.setPayLoad(payload);
        ProductResponse.setHttpStatus(httpStatus);
        ProductResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ProductResponse;
    }
}

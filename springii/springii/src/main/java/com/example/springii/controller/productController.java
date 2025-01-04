package com.example.springii.controller;

import com.example.springii.model.product;
import com.example.springii.service.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin

public class productController {
    @Autowired
private productService service;
    @GetMapping("/products")
    public ResponseEntity<List<product>> getallproduct(){

        return new ResponseEntity<>(service.getALlProducts(), HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<product> getProduct(@PathVariable int id){
        product product=service.getProduct(id);
        if(product!=null)
        return new ResponseEntity<>(product,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/product")
    public ResponseEntity<?>addProduct(
            @RequestPart product product,
            @RequestPart MultipartFile imageFile) {
        try {
            System.out.println(product);
            product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        } catch (Exception e) {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


        }
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]>getImageByProductId(@PathVariable int productId){
        product product =service.getProduct(productId);
                byte[] imageFile=product.getImageDate();
                return ResponseEntity.ok()
                         .contentType(MediaType.valueOf(product.getImageType()))
                        .body(imageFile);
    }
@PutMapping("/product/{id}")
    public ResponseEntity<String>updateProduct(@PathVariable int id,@RequestPart product product,
                                               @RequestPart MultipartFile imageFile){
        product product1 =null;
        try {
            product1 = service.updateProduct(id, product, imageFile);
        }
        catch (IOException e){
            return new ResponseEntity<>("failed to update",HttpStatus.BAD_REQUEST);
        }
        if(product!=null)
            return new ResponseEntity<>("updated",HttpStatus.OK);
        else
            return new ResponseEntity<>("failed to update",HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String>deleteProduct(@PathVariable int id){
product product =service.getProduct(id);
if(product!=null) {
    service.deleteProduct(id);
    return new ResponseEntity<>("deleted", HttpStatus.OK);
}
else
    return new ResponseEntity<>("product not found",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/product/search")
    public ResponseEntity<List<product>> searchProducts(@RequestParam String keyword) {
        System.out.println("Searching with keyword: " + keyword);
        List<product> products = productService.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}

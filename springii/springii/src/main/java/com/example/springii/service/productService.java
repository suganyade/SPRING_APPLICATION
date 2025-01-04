package com.example.springii.service;

import com.example.springii.model.product;
import com.example.springii.repository.productRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class productService {
    @Autowired
private productRepo repo;
    public List<product> getALlProducts() {
    return  repo.findAll();
    }

    public product getProduct(int id) {
        return repo.findById(id).orElse(null);
    }

    public product addProduct(product product, MultipartFile imageFile) throws IOException {
    product.setImageName(imageFile.getOriginalFilename());
    product.setImageType(imageFile.getContentType());
    product.setImageDate(imageFile.getBytes());
    return repo.save(product);
    }

    public product updateProduct(int id, product product, MultipartFile imageFile) throws IOException {
        product.setImageDate(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        return  repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public static List<product> searchProducts(String keyword) {
        return productRepo.searchProducts(keyword);
    }
}

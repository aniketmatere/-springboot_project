package com.example.demo.services;

import com.example.demo.dao.AdminStorage;
import com.example.demo.models.Admin;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminStorage adminStorage;

    public boolean createAdminAccount(Admin admin){
        return adminStorage.createAdminAccount(admin);
    }

    public boolean adminLogin(String email, String password)
    {
        return adminStorage.adminLogin(email,password);
    }

    public boolean addProduct(Product product)
    {
        return adminStorage.addProduct(product);
    }

    public ResponseEntity<List<Product>> getProduct()
    {
        return adminStorage.getProduct();
    }

    public boolean updateProduct(Product product){
        return adminStorage.updateProduct(product);
    }

    public ResponseEntity<String> deleteProduct(int productId)
    {
        return adminStorage.deleteProduct(productId);
    }

}

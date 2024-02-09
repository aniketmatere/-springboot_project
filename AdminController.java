package com.example.demo.Controller;

import com.example.demo.models.Admin;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.services.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/createAdminAccount",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAdminAccount(@RequestBody Admin admin)
    {
        ResponseEntity<String> responseEntity;
        if (adminService.createAdminAccount(admin))
            responseEntity = new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
        else
            responseEntity = new ResponseEntity<>("Account creation failed",HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @PostMapping(value = "/adminLogin/{email}/{password}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> adminLogin(@PathVariable(name = "email") String email,
                                             @PathVariable(name = "password") String password)
    {
        ResponseEntity<String> responseEntity;
        if(adminService.adminLogin(email,password)) {
            responseEntity = new ResponseEntity<>("login success", HttpStatus.ACCEPTED);
            return responseEntity;
        }
        responseEntity = new ResponseEntity<>("Wait for admin approval.", HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;

    }

    @PostMapping(value = "/addProduct",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProduct(@RequestBody Product product)
    {
        ResponseEntity<String> responseEntity;
        if (adminService.addProduct(product))
            responseEntity = new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
        else
            responseEntity = new ResponseEntity<>("Product addition failed",HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @GetMapping(value = "/getProduct",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getProduct()
    {
        try {
            return adminService.getProduct();
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/updateProduct",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProduct(@RequestBody Product product)
    {
        ResponseEntity<String> responseEntity;
        if (adminService.updateProduct(product))
            responseEntity = new ResponseEntity<>("Product updated successfully", HttpStatus.CREATED);
        else
            responseEntity = new ResponseEntity<>("Product updation failed",HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @DeleteMapping(value = "/deleteProduct/{productId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "productId") int productId)
    {
        try {
            return adminService.deleteProduct(productId);
        }catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return new ResponseEntity<>("Something went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

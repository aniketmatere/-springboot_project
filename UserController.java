package com.example.demo.Controller;

import com.example.demo.models.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/createAccount", consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAccount(@RequestBody User user)
    {
        ResponseEntity<String> responseEntity;
        if(userService.createAccount(user))
            responseEntity = new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
        else
            responseEntity = new ResponseEntity<>("Account creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @PostMapping(value = "/login/{email}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@PathVariable(name = "email") String email,
                                        @PathVariable(name = "password") String password) {
        System.out.println(email);
        ResponseEntity<String> responseEntity;
            if(userService.login(email,password)) {
                responseEntity = new ResponseEntity<>("login success", HttpStatus.ACCEPTED);
                return responseEntity;
        }
        responseEntity = new ResponseEntity<>("Wait for admin approval.", HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }
}

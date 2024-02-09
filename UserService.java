package com.example.demo.services;

//import com.example.demo.dao.AdminStorage;
import com.example.demo.dao.UserStorage;
//import com.example.demo.models.Admin;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserStorage userStorage;
    public boolean createAccount(User user)
    {
        return userStorage.createAccount(user);
    }

    public boolean login(String email,String password){
        return userStorage.login(email,password);
    }

}

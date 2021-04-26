package com.example.drivingschool.service;


import com.example.drivingschool.model.User;

public interface AuthService {
    User login(String username, String password);
}

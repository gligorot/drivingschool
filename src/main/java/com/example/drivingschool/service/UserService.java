package com.example.drivingschool.service;

import com.example.drivingschool.model.Role;
import com.example.drivingschool.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String name, String surname, String phoneNumber, Collection<Role> roles);

    User addRoleForUserWithUsername(String roleName, String username);

    List<User> getAll();
}

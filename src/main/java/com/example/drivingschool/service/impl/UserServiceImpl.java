package com.example.drivingschool.service.impl;

import com.example.drivingschool.model.Role;
import com.example.drivingschool.model.User;
import com.example.drivingschool.model.exceptions.InvalidUsernameOrPasswordException;
import com.example.drivingschool.model.exceptions.PasswordsDoNotMatchException;
import com.example.drivingschool.model.exceptions.UsernameAlreadyExistsException;
import com.example.drivingschool.repository.RoleRepository;
import com.example.drivingschool.repository.UserRepository;
import com.example.drivingschool.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(s));
    }

    @Override
    public User addRoleForUserWithUsername(String roleName, String username) {
        UserDetails userDetails = loadUserByUsername(username);
        User user = (User) userDetails; // User extends UserDetails

        Role role = roleRepository.findRoleByNameEquals("ROLE_" + roleName);

        if (!user.hasRoleWithName(roleName)) {
            user.addRole(role);
            user = userRepository.save(user);
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, String phoneNumber, Collection<Role> roles) {
        if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        User user = new User(username,passwordEncoder.encode(password),name,surname, phoneNumber,roles);
        return userRepository.save(user);
    }
}

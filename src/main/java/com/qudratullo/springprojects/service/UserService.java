package com.qudratullo.springprojects.service;

import com.qudratullo.springprojects.model.dto.UserRequest;
import com.qudratullo.springprojects.model.dto.UserResponse;
import com.qudratullo.springprojects.model.User;
import com.qudratullo.springprojects.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest request) {
        User user = new User(request.getUsername(), request.getFullName(), request.getEmail());
        System.out.println(user);
        userRepository.saveUser(user);
        return UserResponse.build(user);
    }
}

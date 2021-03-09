package com.qudratullo.springprojects.service;

import com.qudratullo.springprojects.model.dto.GetUsersResponse;
import com.qudratullo.springprojects.model.dto.UserRequest;
import com.qudratullo.springprojects.model.dto.UserResponse;
import com.qudratullo.springprojects.model.User;
import com.qudratullo.springprojects.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final UserRepository userRepository;
    private final HazelcastService hazelcastService;

    @Value("${datasource.use-hazelcast:false}")
    private boolean useHazelcast;

    @Autowired
    public UserService(UserRepository userRepository, HazelcastService hazelcastService) {
        this.userRepository = userRepository;
        this.hazelcastService = hazelcastService;
    }

    public UserResponse createUser(UserRequest request) {
        User user = new User(request.getUsername(), request.getFullName(), request.getEmail());
        System.out.println(user);
        userRepository.saveUser(user);
        hazelcastService.addUser(user);
        return UserResponse.build(user);
    }

    public GetUsersResponse getAllUsers() {
        if (useHazelcast) {
            return GetUsersResponse.withUsers(hazelcastService.getUsers());
        } else {
            return GetUsersResponse.withUsers(userRepository.getAllUsers());
        }
    }
}

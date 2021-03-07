package com.qudratullo.springprojects.controller;

import com.qudratullo.springprojects.model.dto.UserRequest;
import com.qudratullo.springprojects.model.dto.UserResponse;
import com.qudratullo.springprojects.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("create")
    public UserResponse create(@RequestBody UserRequest request) {
        return userService.createUser(request);
    }
}

package com.qudratullo.springprojects.repository;

import com.qudratullo.springprojects.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void saveUser(User user);

    Optional<User> findById(Long userId);

    List<User> getAllUsers();
}

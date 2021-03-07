package com.qudratullo.springprojects.model.dto;

import com.qudratullo.springprojects.model.User;

import java.util.Objects;

public class UserResponse {

    private Long userId;
    private String username;
    private String fullName;
    private String email;

    public UserResponse() {
    }

    public static UserResponse build(User user) {
        UserResponse response = new UserResponse();
        response.userId = user.getId();
        response.username = user.getUsername();
        response.fullName = user.getFullName();
        response.email = user.getEmail();
        return response;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(userId, that.userId) && Objects.equals(username, that.username) && Objects.equals(fullName, that.fullName) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, fullName, email);
    }
}

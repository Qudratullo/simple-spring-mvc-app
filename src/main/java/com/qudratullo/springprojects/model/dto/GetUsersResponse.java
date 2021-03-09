package com.qudratullo.springprojects.model.dto;

import com.qudratullo.springprojects.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class GetUsersResponse {

    private List<UserResponse> users;

    public GetUsersResponse() {
    }

    public static GetUsersResponse withUsers(List<User> users) {
        GetUsersResponse response = new GetUsersResponse();
        response.users = users.stream().map(UserResponse::build).collect(Collectors.toList());
        return response;
    }

    public List<UserResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponse> users) {
        this.users = users;
    }
}

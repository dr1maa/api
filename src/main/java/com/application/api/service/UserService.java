package com.application.api.service;


import com.application.api.model.User;

public interface UserService {

    User getUserById(User userId);

    User register(User user);

    User getUserInfo(User user);

    User updateUser(User user, User updatedUser);

    void deleteUser(User user);
    User getUserByUsername(String username);

}
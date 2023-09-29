package com.application.api.service;


import com.application.api.model.User;

import java.util.Optional;

public interface UserService {

    User getUserById(Long id);

    User register(User user);

    User getUserInfo(User user);

    User updateUser(String username, User updatedUser);

    void deleteUser(User user);
    User getUserByUsername(String username);

}
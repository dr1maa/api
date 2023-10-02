package com.application.api.service;

import com.application.api.model.Enum.Role;
import com.application.api.model.User;
import com.application.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.customFindById(id);
    }

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Пользователь с таким Email уже существует");
        }
        if (userRepository.findByUsername(user.getName()) != null) {
            return null;
        }
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRole(roles);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    @Override
    public User getUserInfo(User user) {
        User thisUser = userRepository.customFindById(user.getUserId());
        if (thisUser == null) {
            return null;
        }
//        String username = user.getName();
//        String email = user.getEmail();
//        Set<Role> roles = user.getRole();
        return thisUser;
    }

    @Override
    public User updateUser(String username, User updatedUser) {
        User thisUser = userRepository.findByUsername(username);
        thisUser.setUsername(updatedUser.getUsername());
        thisUser.setName(updatedUser.getName());
        return userRepository.save(thisUser);
    }

    @Override
    public void deleteUser(User user) {
        User thisUser = userRepository.customFindById(user.getUserId());
        if (thisUser == null) {
            throw new RuntimeException("Пользователь не существует");
        }
        userRepository.deleteById(user.getUserId());
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
package com.application.api.controllers;

import com.application.api.model.User;
import com.application.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerNewUser(@RequestBody User user) {
        User registeredUser = userService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUserInfo(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateCurrentUser(@RequestBody User updatedUser, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.updateUser( username, updatedUser);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/me")
    public ResponseEntity<User> deleteCurrentUser(Authentication authentication){
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }


}

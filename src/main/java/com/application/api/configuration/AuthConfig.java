package com.application.api.configuration;

import com.application.api.service.CustomAuthenticationProvider;
import com.application.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
public class AuthConfig {

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth, UserService userService) {
        auth.authenticationProvider(new CustomAuthenticationProvider(userService));
    }

}
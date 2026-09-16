package com.example.SecureLoginPUC.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final InMemoryUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(InMemoryUserDetailsManager userDetailsManager,
                       PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(String email, String senha) {

        UserDetails user = User.builder()
                .username(email)
                .password(passwordEncoder.encode(senha))
                .roles("USER")
                .build();

        userDetailsManager.createUser(user);
    }

    public boolean exists(String email) {
        return userDetailsManager.userExists(email);
    }
}
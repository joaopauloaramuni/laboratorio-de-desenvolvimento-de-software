package com.example.JWT_RestAPI.service;

import com.example.JWT_RestAPI.dao.UserDAO;
import com.example.JWT_RestAPI.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public Optional<UserEntity> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public UserEntity save(UserEntity user) {
        return userDAO.save(user);
    }

}

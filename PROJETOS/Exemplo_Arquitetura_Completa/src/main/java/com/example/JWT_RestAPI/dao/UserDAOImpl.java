package com.example.JWT_RestAPI.dao;

import com.example.JWT_RestAPI.model.UserEntity;
import com.example.JWT_RestAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDAOImpl implements UserDAO {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}

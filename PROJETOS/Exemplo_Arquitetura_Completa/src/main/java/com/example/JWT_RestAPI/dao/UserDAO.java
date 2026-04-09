package com.example.JWT_RestAPI.dao;

import com.example.JWT_RestAPI.model.UserEntity;

import java.util.Optional;

public interface UserDAO {

    Optional<UserEntity> findByUsername(String username);

    UserEntity save(UserEntity userEntity);

}

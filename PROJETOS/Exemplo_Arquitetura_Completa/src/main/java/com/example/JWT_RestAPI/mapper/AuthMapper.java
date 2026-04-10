package com.example.JWT_RestAPI.mapper;

import com.example.JWT_RestAPI.dto.LoginResponseDTO;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class AuthMapper {

    public static LoginResponseDTO toLoginResponse(String token, Collection<? extends GrantedAuthority> authorities) {
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).toList();
        return new LoginResponseDTO(token, roles);
    }

}
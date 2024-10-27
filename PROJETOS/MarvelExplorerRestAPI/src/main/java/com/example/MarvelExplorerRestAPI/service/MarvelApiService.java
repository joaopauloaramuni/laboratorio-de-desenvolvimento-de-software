package com.example.MarvelExplorerRestAPI.service;

import com.example.MarvelExplorerRestAPI.config.MarvelApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class MarvelApiService {

    @Autowired
    private MarvelApiConfig marvelApiConfig;

    public String buildUrl(String endpoint) {
        long timestamp = System.currentTimeMillis();
        String hash = generateHash(timestamp);
        return marvelApiConfig.getBaseUrl() + endpoint + "?apikey=" + marvelApiConfig.getPublicKey() +
                "&ts=" + timestamp + "&hash=" + hash;
    }

    private String generateHash(long timestamp) {
        String input = timestamp + marvelApiConfig.getPrivateKey() + marvelApiConfig.getPublicKey();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }
}

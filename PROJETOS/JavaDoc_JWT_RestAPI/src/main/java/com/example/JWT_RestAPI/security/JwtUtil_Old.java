package com.example.JWT_RestAPI.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

// Os métodos usados nesta classe são de uma versão antiga do jjwt
@Deprecated
public class JwtUtil_Old {
    // É comum guardar essa SECRET_KEY separadamente em um arquivo de configuração
    private static final String SECRET_KEY = generateSecretKey();
    private static final long EXPIRATION_TIME = 864_000_000;
    // 10 dias de validade para o token

    private static String generateSecretKey() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String secretString =  Encoders.BASE64.encode(key.getEncoded());
        System.out.println("Secret Key: " + secretString);
        return secretString;
    }

    public static String generateToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        System.out.println("Token: " + token);
        return token;
    }
    public static String extractUsername(String token) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
        return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token).
                getPayload().getSubject();
    }
}

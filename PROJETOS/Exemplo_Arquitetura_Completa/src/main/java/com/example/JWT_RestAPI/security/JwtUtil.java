package com.example.JWT_RestAPI.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Classe utilitária responsável pela criação e validação de tokens JWT.
 *
 * <p>Utiliza a biblioteca JJWT (Classe atualizada para a versão 0.12.3 do jjwt) para geração e parsing
 * de tokens assinados com algoritmo HS512.</p>
 *
 * <p>Inclui funcionalidades para:</p>
 * <ul>
 *     <li>Geração de chave secreta</li>
 *     <li>Codificação da chave em Base64</li>
 *     <li>Geração de tokens JWT</li>
 *     <li>Extração de informações (username) do token</li>
 * </ul>
 *
 * <p><b>Observação:</b> Em ambientes reais, a chave secreta deve ser armazenada
 * de forma segura (ex: variáveis de ambiente ou arquivos de configuração),
 * evitando geração dinâmica a cada execução.</p>
 */
public class JwtUtil {

    /**
     * Chave secreta utilizada para assinar os tokens JWT.
     */
    private static final SecretKey SECRET_KEY = generateSecretKey();
    // É comum guardar essa SECRET_KEY separadamente em um arquivo de configuração

    /**
     * Representação da chave secreta em formato Base64.
     */
    private static final String SECRET_STRING = getSecretString();

    /**
     * Tempo de expiração do token em milissegundos (10 dias).
     */
    private static final long EXPIRATION_TIME = 864_000_000;

    /**
     * Gera uma nova chave secreta utilizando o algoritmo HS512.
     *
     * @return chave secreta {@link SecretKey}
     */
    private static SecretKey generateSecretKey() {
        SecretKey key = Jwts.SIG.HS512.key().build();
        return key;
    }

    /**
     * Converte a chave secreta para uma string codificada em Base64.
     *
     * <p>Também imprime a chave no console para fins de depuração.</p>
     *
     * @return string Base64 da chave secreta
     */
    private static String getSecretString() {
        String secretString =  Encoders.BASE64.encode(SECRET_KEY.getEncoded());
        System.out.println("Secret Key: " + secretString);
        return secretString;
    }

    /**
     * Gera um token JWT para o nome de usuário fornecido.
     *
     * <p>O token inclui:</p>
     * <ul>
     *     <li>Subject (username)</li>
     *     <li>Data de expiração</li>
     *     <li>Assinatura com chave secreta</li>
     * </ul>
     *
     * @param username nome de usuário que será incluído no token
     * @return token JWT gerado
     */
    public static String generateToken(String username) {
        String token = Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
        System.out.println("Token: " + token);
        return token;
    }

    /**
     * Extrai o nome de usuário (subject) de um token JWT.
     *
     * <p>Realiza a validação da assinatura utilizando a chave secreta
     * decodificada em Base64.</p>
     *
     * @param token token JWT a ser analisado
     * @return nome de usuário contido no token
     */
    public static String extractUsername(String token) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_STRING));
        return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token).
                getPayload().getSubject();
    }
}

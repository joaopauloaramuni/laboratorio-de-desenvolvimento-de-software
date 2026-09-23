package com.example.Captcha.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * =========================================================
 * ALTCHA (prova de trabalho / proof-of-work)
 * ---------------------------------------------------------
 * Em vez de mostrar imagens, o navegador precisa "gastar"
 * um pouco de processamento para achar um número secreto.
 * Para uma pessoa leva menos de um segundo; para um robô
 * enviando milhares de formulários fica caro.
 *
 * Não depende de nenhuma empresa: o desafio é criado e
 * conferido aqui mesmo, só com classes do Java.
 *
 * Como funciona:
 *  1. O servidor sorteia um "salt" e um número secreto N
 *  2. Envia: challenge = SHA-256(salt + N)  (sem o N!)
 *     e uma assinatura HMAC do challenge
 *  3. O navegador testa 0, 1, 2, ... até achar o N que
 *     gera o mesmo hash
 *  4. O servidor confere o hash, a assinatura e o prazo
 *
 * Campo enviado pelo formulário: altcha (texto em Base64)
 * =========================================================
 */
@Service
public class AltchaService {

    private static final String ALGORITMO = "SHA-256";

    @Value("${altcha.hmac-key}")
    private String hmacKey;

    @Value("${altcha.max-number:100000}")
    private long maxNumber;

    @Value("${altcha.validade-minutos:10}")
    private long validadeMinutos;

    /**
     * Desafios já usados, para ninguém reenviar a mesma solução.
     * Em produção, troque por um cache com expiração (ex.: Redis).
     */
    private final Set<String> desafiosUsados = ConcurrentHashMap.newKeySet();

    private final SecureRandom random = new SecureRandom();

    private final ObjectMapper objectMapper;

    public AltchaService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /** Cria o desafio que o widget busca em /altcha/desafio. */
    public Map<String, Object> criarDesafio() throws Exception {

        byte[] bytes = new byte[12];
        random.nextBytes(bytes);

        long expira = Instant.now().plusSeconds(validadeMinutos * 60).getEpochSecond();

        // O prazo vai dentro do salt, então também fica protegido pela assinatura
        String salt = HexFormat.of().formatHex(bytes) + "?expires=" + expira;
        long numeroSecreto = random.nextLong(maxNumber + 1);

        String challenge = sha256(salt + numeroSecreto);
        String signature = hmacSha256(challenge);

        Map<String, Object> desafio = new LinkedHashMap<>();
        desafio.put("algorithm", ALGORITMO);
        desafio.put("challenge", challenge);
        desafio.put("maxnumber", maxNumber);
        desafio.put("salt", salt);
        desafio.put("signature", signature);
        return desafio;
    }

    /** Confere a solução que o widget colocou no campo "altcha". */
    public boolean validar(String payloadBase64) {

        if (payloadBase64 == null || payloadBase64.isBlank()) {
            return false;
        }

        try {
            String json = new String(Base64.getDecoder().decode(payloadBase64), StandardCharsets.UTF_8);
            JsonNode payload = objectMapper.readTree(json);

            String algoritmo = payload.path("algorithm").asText();
            String challenge = payload.path("challenge").asText();
            String salt = payload.path("salt").asText();
            String signature = payload.path("signature").asText();
            long numero = payload.path("number").asLong(-1);

            if (!ALGORITMO.equals(algoritmo) || numero < 0) {
                return false;
            }

            // 1. O prazo não pode ter passado
            if (expirou(salt)) {
                return false;
            }

            // 2. O número encontrado precisa gerar o mesmo hash
            if (!iguais(sha256(salt + numero), challenge)) {
                return false;
            }

            // 3. A assinatura prova que fomos NÓS que criamos o desafio
            if (!iguais(hmacSha256(challenge), signature)) {
                return false;
            }

            // 4. Cada desafio só pode ser usado uma vez
            return desafiosUsados.add(challenge);

        } catch (Exception e) {
            System.out.println("Erro ao validar ALTCHA: " + e.getMessage());
            return false;
        }
    }

    private boolean expirou(String salt) {
        int posicao = salt.indexOf("expires=");
        if (posicao < 0) {
            return true;
        }
        String valor = salt.substring(posicao + "expires=".length()).split("&")[0];
        return Instant.now().getEpochSecond() > Long.parseLong(valor);
    }

    private String sha256(String texto) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return HexFormat.of().formatHex(digest.digest(texto.getBytes(StandardCharsets.UTF_8)));
    }

    private String hmacSha256(String texto) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(hmacKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return HexFormat.of().formatHex(mac.doFinal(texto.getBytes(StandardCharsets.UTF_8)));
    }

    /** Comparação em tempo constante, para não vazar informação pelo tempo. */
    private boolean iguais(String a, String b) {
        return MessageDigest.isEqual(
                a.getBytes(StandardCharsets.UTF_8),
                b.getBytes(StandardCharsets.UTF_8));
    }
}

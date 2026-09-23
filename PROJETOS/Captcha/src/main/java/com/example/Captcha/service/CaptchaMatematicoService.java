package com.example.Captcha.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

/**
 * =========================================================
 * CAPTCHA MATEMÁTICO (gerado pelo próprio projeto)
 * ---------------------------------------------------------
 * O mais simples de todos: uma conta de somar, subtrair ou
 * multiplicar. A resposta fica guardada na SESSÃO, nunca
 * no HTML (senão um robô leria a resposta na página).
 *
 * Bom para aprender o conceito. Contra robôs de verdade é
 * fraco, porque um programa resolve a conta facilmente.
 *
 * Campo enviado pelo formulário: resposta
 * =========================================================
 */
@Service
public class CaptchaMatematicoService {

    private static final String CHAVE_SESSAO = "CAPTCHA_MATEMATICO";

    private final SecureRandom random = new SecureRandom();

    /** Cria uma pergunta nova e guarda a resposta na sessão. */
    public String gerarPergunta(HttpSession session) {

        int a = 1 + random.nextInt(9);
        int b = 1 + random.nextInt(9);
        int operacao = random.nextInt(3);

        String pergunta;
        int resposta;

        switch (operacao) {
            case 0 -> {
                pergunta = a + " + " + b;
                resposta = a + b;
            }
            case 1 -> {
                // Maior primeiro, para o resultado nunca ser negativo
                int maior = Math.max(a, b);
                int menor = Math.min(a, b);
                pergunta = maior + " − " + menor;
                resposta = maior - menor;
            }
            default -> {
                pergunta = a + " × " + b;
                resposta = a * b;
            }
        }

        session.setAttribute(CHAVE_SESSAO, resposta);
        return "Quanto é " + pergunta + "?";
    }

    /** Confere a resposta e invalida a pergunta. */
    public boolean validar(HttpSession session, String respostaUsuario) {

        Object esperado = session.getAttribute(CHAVE_SESSAO);
        session.removeAttribute(CHAVE_SESSAO);

        if (esperado == null || respostaUsuario == null) {
            return false;
        }
        try {
            return Integer.parseInt(respostaUsuario.trim()) == (Integer) esperado;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

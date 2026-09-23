package com.example.Captcha.service;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

/**
 * =========================================================
 * CAPTCHA DE IMAGEM (gerado pelo próprio projeto)
 * ---------------------------------------------------------
 * Funciona sem internet e sem cadastro em nenhum serviço.
 *
 * Fluxo:
 *  1. O navegador pede a imagem em /captcha-imagem/imagem
 *  2. O servidor sorteia um texto, guarda na SESSÃO e
 *     desenha esse texto em um PNG com ruído
 *  3. O usuário digita o texto e envia o formulário
 *  4. O servidor compara com o que está na sessão e
 *     apaga o texto (cada captcha só vale uma vez)
 *
 * Campo enviado pelo formulário: captcha
 * =========================================================
 */
@Service
public class CaptchaImagemService {

    private static final String CHAVE_SESSAO = "CAPTCHA_IMAGEM";

    // Sem 0/O, 1/I/L, 5/S, 2/Z, 8/B: letras fáceis de confundir
    private static final String CARACTERES = "ACDEFGHJKMNPQRTUVWXY34679";

    private static final int LARGURA = 180;
    private static final int ALTURA = 60;

    @Value("${captcha.imagem.tamanho:5}")
    private int tamanho;

    private final SecureRandom random = new SecureRandom();

    /** Sorteia um texto novo, guarda na sessão e devolve o PNG. */
    public byte[] gerarImagem(HttpSession session) throws IOException {

        String texto = gerarTexto();
        session.setAttribute(CHAVE_SESSAO, texto);

        BufferedImage imagem = new BufferedImage(LARGURA, ALTURA, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = imagem.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fundo
        g.setColor(new Color(244, 246, 250));
        g.fillRect(0, 0, LARGURA, ALTURA);

        // Linhas de ruído ATRÁS do texto
        desenharRuido(g, 8);

        // Cada letra com tamanho, cor e inclinação diferentes
        int x = 16;
        for (char c : texto.toCharArray()) {
            int tamanhoFonte = 28 + random.nextInt(8);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, tamanhoFonte));
            g.setColor(new Color(random.nextInt(90), random.nextInt(90), 60 + random.nextInt(110)));

            double angulo = Math.toRadians(random.nextInt(50) - 25);
            int y = 38 + random.nextInt(12);

            AffineTransform original = g.getTransform();
            g.rotate(angulo, x, y);
            g.drawString(String.valueOf(c), x, y);
            g.setTransform(original);

            x += 28 + random.nextInt(4);
        }

        // Mais algumas linhas POR CIMA do texto
        desenharRuido(g, 3);

        g.dispose();

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        ImageIO.write(imagem, "png", saida);
        return saida.toByteArray();
    }

    /** Compara a resposta do usuário e invalida o captcha. */
    public boolean validar(HttpSession session, String resposta) {

        Object esperado = session.getAttribute(CHAVE_SESSAO);

        // Remove SEMPRE: errou ou acertou, precisa de uma imagem nova
        session.removeAttribute(CHAVE_SESSAO);

        if (esperado == null || resposta == null) {
            return false;
        }
        return esperado.toString().equalsIgnoreCase(resposta.trim());
    }

    private String gerarTexto() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            sb.append(CARACTERES.charAt(random.nextInt(CARACTERES.length())));
        }
        return sb.toString();
    }

    private void desenharRuido(Graphics2D g, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            g.setColor(new Color(120 + random.nextInt(100), 120 + random.nextInt(100), 120 + random.nextInt(100)));
            g.setStroke(new BasicStroke(1 + random.nextInt(2)));
            g.drawLine(random.nextInt(LARGURA), random.nextInt(ALTURA),
                    random.nextInt(LARGURA), random.nextInt(ALTURA));
        }
    }
}

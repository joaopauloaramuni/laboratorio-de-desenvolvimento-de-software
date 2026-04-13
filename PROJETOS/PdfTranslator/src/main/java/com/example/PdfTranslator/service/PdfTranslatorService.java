package com.example.PdfTranslator.service;

import com.example.PdfTranslator.dto.GoogleTranslateResponseDTO;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfTranslatorService {

    private static final int MAX_CHARS = 4500;
    private static final long PAUSE_MS = 800;

    private static final String GT_URL =
            "https://translate.googleapis.com/translate_a/single" +
                    "?client=gtx&sl=%s&tl=%s&dt=t&q=%s";

    private final RestTemplate restTemplate = new RestTemplate();

    // ─────────────────────────────────────────────
    // ENTRY POINT
    // ─────────────────────────────────────────────

    public byte[] translatePdfToTxt(MultipartFile file)
            throws IOException, InterruptedException {

        System.out.println("\n========== START PDF TRANSLATION ==========");

        // 1. Extrai texto do PDF
        String text = extractTextFromPdf(file);
        System.out.println("[RAW] " + shortText(text, 300));

        // 2. Normaliza encoding do texto extraído
        text = normalizeEncoding(text);
        System.out.println("[NORMALIZED] " + shortText(text, 300));

        // 3. Limpa o texto
        text = cleanText(text);
        System.out.println("[CLEAN] " + shortText(text, 300));

        // 4. Divide em blocos
        List<String> blocos = split(text);
        System.out.println("[BLOCKS] " + blocos.size());

        StringBuilder out = new StringBuilder();

        for (int i = 0; i < blocos.size(); i++) {

            String bloco = blocos.get(i);

            System.out.println("\n--- BLOCO " + (i + 1) + " ---");
            System.out.println("[SIZE] " + bloco.length());
            System.out.println("[TEXT] " + shortText(bloco, 200));

            // 5. Traduz o bloco
            String translated = translate(bloco, "en", "pt-BR");

            System.out.println("[TRANSLATED] " + shortText(translated, 200));
            System.out.println("[SAME?] " + bloco.equals(translated));

            out.append(translated).append("\n\n");

            // Pausa para não sobrecarregar a API
            Thread.sleep(PAUSE_MS);
        }

        System.out.println("========== END ==========");

        return out.toString().getBytes(StandardCharsets.UTF_8);
    }

    // ─────────────────────────────────────────────
    // PDF EXTRACTION
    // ─────────────────────────────────────────────

    private String extractTextFromPdf(MultipartFile file) throws IOException {

        byte[] bytes = file.getInputStream().readAllBytes();

        try (PDDocument doc = Loader.loadPDF(bytes)) {

            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);

            return stripper.getText(doc);
        }
    }

    // ─────────────────────────────────────────────
    // ENCODING NORMALIZATION (para texto extraído)
    // ─────────────────────────────────────────────

    private String normalizeEncoding(String text) {

        if (text == null) return null;

        String t = text;

        // 1. Corrige "% 2C" → "%2C"
        t = t.replaceAll("%\\s+([0-9A-Fa-f]{2})", "%$1");

        // 2. Corrige double encoding (%25 → %)
        t = t.replace("%25", "%");

        // 3. Decode múltiplo seguro
        try {
            for (int i = 0; i < 3; i++) {
                t = URLDecoder.decode(t, StandardCharsets.UTF_8);
            }
        } catch (Exception ignored) {
            // Se falhar, continua com o texto atual
        }

        // 4. Remove lixo restante
        t = t.replaceAll("%[0-9A-Fa-f]{2}", " ");

        return t;
    }

    // ─────────────────────────────────────────────
    // CLEAN
    // ─────────────────────────────────────────────

    private String cleanText(String text) {

        return text
                .replaceAll("\\r\\n", "\n")
                .replaceAll("\\n{3,}", "\n\n")
                .replaceAll("\\s+", " ")
                .replaceAll(" +", " ")
                .trim();
    }

    // ─────────────────────────────────────────────
    // SPLIT
    // ─────────────────────────────────────────────

    private List<String> split(String text) {

        String[] paragraphs = text.split("\\n\\n+");

        List<String> out = new ArrayList<>();
        StringBuilder buf = new StringBuilder();

        for (String p : paragraphs) {

            p = p.trim();
            if (p.isEmpty()) continue;

            if (p.length() > MAX_CHARS) {

                String[] sentences = p.split("(?<=[.!?])\\s+");

                for (String s : sentences) {
                    add(buf, out, s);
                }

            } else {
                add(buf, out, p);
            }
        }

        if (!buf.isEmpty()) out.add(buf.toString());

        return out;
    }

    private void add(StringBuilder buf, List<String> out, String text) {

        if (text.length() > MAX_CHARS) {
            flush(buf, out);
            out.add(text);
            return;
        }

        if (buf.length() + text.length() <= MAX_CHARS) {

            if (!buf.isEmpty()) buf.append("\n\n");

            buf.append(text);

        } else {
            flush(buf, out);
            buf.append(text);
        }
    }

    private void flush(StringBuilder buf, List<String> out) {
        if (!buf.isEmpty()) {
            out.add(buf.toString());
            buf.setLength(0);
        }
    }

    // ─────────────────────────────────────────────
    // TRANSLATION
    // ─────────────────────────────────────────────

    private String translate(String text, String from, String to)
            throws InterruptedException {

        for (int i = 1; i <= 3; i++) {

            try {
                String encoded = URLEncoder.encode(text, StandardCharsets.UTF_8);

                String url = String.format(GT_URL, from, to, encoded);

                List<?> resp = restTemplate.getForObject(url, List.class);

                if (resp == null || resp.isEmpty()) return text;

                GoogleTranslateResponseDTO dto =
                        GoogleTranslateResponseDTO.from(resp);

                String translated = dto.getTranslatedText();

                if (translated != null && !translated.isBlank()) {

                    // 🔥🔥🔥 PÓS-PROCESSAMENTO DA TRADUÇÃO 🔥🔥🔥
                    String cleaned = postProcessTranslation(translated);

                    // Se a tradução veio vazia após limpeza, retorna o original
                    if (cleaned == null || cleaned.isBlank()) {
                        System.out.println("[WARN] Tradução vazia após limpeza, usando original");
                        return text;
                    }

                    return cleaned;
                }

            } catch (Exception e) {

                System.out.println("[ERROR] Tentativa " + i + ": " + e.getMessage());

                if (i < 3) {
                    Thread.sleep(i * 1000L);
                }
            }
        }

        System.out.println("[FALLBACK] Todas as tentativas falharam, retornando original");
        return text;
    }

    // ─────────────────────────────────────────────
    // POST-PROCESSAMENTO DA TRADUÇÃO
    // ─────────────────────────────────────────────

    /**
     * Remove artefatos de URL-encoding residuais da resposta do Google Translate.
     * A API às vezes retorna texto com %0A, %2C, %20, % 2C, etc.
     */
    private String postProcessTranslation(String translated) {

        if (translated == null || translated.isEmpty()) {
            return translated;
        }

        String t = translated;

        // 1. Corrige encoding quebrado com espaços
        t = t.replaceAll("%\\s+([0-9A-Fa-f]{2})", "%$1");
        t = t.replace("%25", "%");

        // 2. Decodifica URL encoding
        String previous;
        int iteration = 0;
        do {
            previous = t;
            try {
                t = URLDecoder.decode(t, StandardCharsets.UTF_8);
                iteration++;
            } catch (Exception e) {
                break;
            }
        } while (!t.equals(previous) && iteration < 5);

        // 3. Remove %XX residuais
        t = t.replaceAll("%[0-9A-Fa-f]{2}", " ");

        // 4. Converte escapes
        t = t.replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t");

        // 5. Correção de pontuação duplicada
        // Sequências de 2+ vírgulas → 1 vírgula
        t = t.replaceAll(",,+", ",");

        // 5b. Letra maiúscula residual do original colada na tradução
        // "SAssim" → "Assim", "SEntão" → "Então", "MGrande" → "Grande"
        t = t.replaceAll("^([A-Z])([A-Z][a-záéíóúãõàèìòùâêîôûçüñ]+)", "$2");
        t = t.replaceAll("(?<=[\\s\\n])([A-Z])([A-Z][a-záéíóúãõàèìòùâêîôûçüñ]+)", "$2");

        // 6. Corrige espaçamento de pontuação
        t = t.replaceAll("\\s+([,.;:!?])", "$1");

        // Combinações de ponto-e-vírgula com vírgula
        t = t.replaceAll(";,", ";");
        t = t.replaceAll(",;", ";");

        // Outras pontuações duplicadas
        t = t.replaceAll("\\.\\.+", ".");
        t = t.replaceAll("!!+", "!");
        t = t.replaceAll("\\?\\?+", "?");
        t = t.replaceAll(":;", ":");
        t = t.replaceAll(";:", ";");

        // 7. Corrige espaçamento de pontuação
        t = t.replaceAll("\\s+([,.;:!?])", "$1");      // remove espaço antes
        t = t.replaceAll("([,.;:!?])(?=[^\\s])", "$1 "); // adiciona espaço depois

        // 8. Limpeza final
        t = t.replaceAll("\\s+", " ").trim();

        // 9. Restaura quebras de linha
        t = t.replace("%0A", "\n");

        return t;
    }

    // ─────────────────────────────────────────────
    // UTIL
    // ─────────────────────────────────────────────

    private String shortText(String t, int max) {
        if (t == null) return "null";
        t = t.replace("\n", " ").replace("\r", " ");
        return t.length() <= max ? t : t.substring(0, max) + "...";
    }
}

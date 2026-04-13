package com.example.PdfTranslator.dto;

import java.util.List;

/**
 * DTO para a resposta do Google Translate não-oficial.
 *
 * O JSON retornado é um array puro (sem chaves), com este formato:
 *
 * [
 *   [                                      ← índice 0: sentenças traduzidas
 *     ["frase traduzida", "original", ...],
 *     ["outra frase",     "other",    ...],
 *   ],
 *   null,
 *   "en"
 * ]
 *
 * O RestTemplate deserializa isso como List<?>.
 * O método estático from() converte para este DTO.
 */
public class GoogleTranslateResponseDTO {

    // Cada elemento é uma sentença: índice 0 = traduzido, índice 1 = original
    private final List<List<Object>> sentences;

    private GoogleTranslateResponseDTO(List<List<Object>> sentences) {
        this.sentences = sentences;
    }

    /**
     * Constrói o DTO a partir do List<?> bruto devolvido pelo RestTemplate.
     * rawResponse.get(0) é a lista de sentenças traduzidas.
     */
    @SuppressWarnings("unchecked")
    public static GoogleTranslateResponseDTO from(List<?> rawResponse) {
        if (rawResponse == null || rawResponse.isEmpty() || !(rawResponse.get(0) instanceof List)) {
            return new GoogleTranslateResponseDTO(List.of());
        }
        return new GoogleTranslateResponseDTO((List<List<Object>>) rawResponse.get(0));
    }

    /**
     * Concatena os índices [0] de cada sentença.
     * Retorna a string bruta - o pós-processamento será feito no serviço.
     */
    public String getTranslatedText() {
        if (sentences == null || sentences.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (List<Object> sentenca : sentences) {
            if (sentenca != null && !sentenca.isEmpty() && sentenca.get(0) != null) {
                sb.append(sentenca.get(0).toString());
            }
        }

        String resultado = sb.toString().trim();

        return resultado.isEmpty() ? null : resultado;
    }

    /**
     * Retorna o texto original (não traduzido) para debug.
     */
    public String getOriginalText() {
        if (sentences == null || sentences.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (List<Object> sentenca : sentences) {
            if (sentenca != null && sentenca.size() > 1 && sentenca.get(1) != null) {
                sb.append(sentenca.get(1).toString());
            }
        }

        return sb.toString().trim();
    }

    /**
     * Retorna o número de sentenças na resposta.
     */
    public int getSentenceCount() {
        return sentences != null ? sentences.size() : 0;
    }

    @Override
    public String toString() {
        return "GoogleTranslateResponseDTO{" +
                "sentences=" + sentences +
                ", translatedText='" + getTranslatedText() + '\'' +
                '}';
    }
}
package com.example.YouTubeVideoDownloader.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ProgressController {

    private static final ConcurrentHashMap<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping(value = "/progress/{sessionId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getProgress(@PathVariable String sessionId) {
        System.out.println("Nova solicitação de progresso recebida para sessionId: " + sessionId);

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(sessionId, emitter);

        emitter.onCompletion(() -> {
            emitters.remove(sessionId);
            System.out.println("Emitter concluído e removido para sessionId: " + sessionId);
        });

        emitter.onTimeout(() -> {
            emitters.remove(sessionId);
            System.out.println("Emitter expirado e removido para sessionId: " + sessionId);
        });

        return emitter;
    }

    public static void sendProgress(String sessionId, String progress) {
        SseEmitter emitter = emitters.get(sessionId);
        if (emitter == null) {
            System.out.println("Emitter é null para sessionId: " + sessionId);
            return;
        }

        try {
            emitter.send(SseEmitter.event().data(progress));
            System.out.println("Progresso enviado para sessionId " + sessionId + ": " + progress + "%");
        } catch (IOException e) {
            emitters.remove(sessionId);
            System.out.println("Erro ao enviar progresso para sessionId " + sessionId + ": " + e.getMessage());
        }
    }

}

package com.example.ZapSender.service;

import com.example.ZapSender.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class WebHookService {

    @Autowired
    private ApiConfig apiConfig;

    @Autowired
    private RestTemplate restTemplate;

    // Controle de perguntas enviadas
    private final Map<String, Object> perguntaEnviada = new HashMap<>();

    /**
     * Envia um template do WhatsApp Cloud API.
     *
     * Exemplo de requisição:
     * POST http://localhost:8080/webook/enviar-template?numeroDestino=5531980402103&nomeTemplate=hello_world&codigoIdioma=en_US
     */
    public String enviarTemplate(String numeroDestino, String nomeTemplate, String codigoIdioma) {
        Map<String, Object> language = new HashMap<>();
        language.put("code", codigoIdioma);

        Map<String, Object> template = new HashMap<>();
        template.put("name", nomeTemplate);
        template.put("language", language);

        Map<String, Object> payload = new HashMap<>();
        payload.put("messaging_product", "whatsapp");
        payload.put("to", numeroDestino);
        payload.put("type", "template");
        payload.put("template", template);

        return enviarRequest(payload);
    }

    /**
     * Envia uma mensagem de texto simples via WhatsApp Cloud API.
     *
     * Exemplo de requisição:
     * POST http://localhost:8080/webhook/enviar-texto?numeroDestino=5531980402103&texto=Olá%20Mundo
     */
    public String enviarMensagemTexto(String numeroDestino, String texto) {
        Map<String, Object> textoMap = new HashMap<>();
        textoMap.put("body", texto);

        Map<String, Object> payload = new HashMap<>();
        payload.put("messaging_product", "whatsapp");
        payload.put("to", numeroDestino);
        payload.put("type", "text");
        payload.put("text", textoMap);

        return enviarRequest(payload);
    }


    /**
     * Envia uma mensagem interativa com botões de opinião via WhatsApp Cloud API.
     *
     * Exemplo de requisição:
     * POST http://localhost:8080/webhook/enviar-botoes?numeroDestino=5531980402103
     */
    public String enviarPerguntaComBotoes(String numeroDestino) {
        Map<String, Object> body = new HashMap<>();
        body.put("text", "Olá! Me diga: o que você achou da oficina de Spring Boot? Sua opinião é muito importante!");

        List<Map<String, Object>> buttons = new ArrayList<>();
        buttons.add(criarBotao("opcao_otima", "Ótima"));
        buttons.add(criarBotao("opcao_boa", "Boa"));
        buttons.add(criarBotao("opcao_regular", "Regular"));

        Map<String, Object> action = new HashMap<>();
        action.put("buttons", buttons);

        Map<String, Object> interactive = new HashMap<>();
        interactive.put("type", "button");
        interactive.put("body", body);
        interactive.put("action", action);

        Map<String, Object> payload = new HashMap<>();
        payload.put("messaging_product", "whatsapp");
        payload.put("to", numeroDestino);
        payload.put("type", "interactive");
        payload.put("interactive", interactive);

        return enviarRequest(payload);
    }

    /**
     * Método auxiliar para criar um botão de reply.
     */
    private Map<String, Object> criarBotao(String id, String title) {
        Map<String, Object> reply = new HashMap<>();
        reply.put("id", id);
        reply.put("title", title);

        Map<String, Object> button = new HashMap<>();
        button.put("type", "reply");
        button.put("reply", reply);

        return button;
    }

    /**
     * Envia o request HTTP para a API do WhatsApp.
     */
    private String enviarRequest(Map<String, Object> payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiConfig.getAccessToken());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiConfig.getApiUrl(), request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return "✅ Mensagem enviada com sucesso!\n" + response.getBody();
            } else {
                return "⚠️ Erro ao enviar mensagem.\nStatus: " + response.getStatusCode() + "\nResposta: " + response.getBody();
            }
        } catch (Exception e) {
            return "❌ Exceção ao enviar mensagem: " + e.getMessage();
        }
    }

    /**
    * Processa o payload recebido do WhatsApp Cloud API.
    *
    * @param payload Map contendo o JSON recebido do webhook do WhatsApp Cloud API
    */
    @SuppressWarnings("unchecked")
    public void processarWebhook(Map<String, Object> payload) {
        try {
            List<Map<String, Object>> entries = (List<Map<String, Object>>) payload.getOrDefault("entry", Collections.emptyList());

            for (Map<String, Object> entry : entries) {
                List<Map<String, Object>> changes = (List<Map<String, Object>>) entry.getOrDefault("changes", Collections.emptyList());
                for (Map<String, Object> change : changes) {
                    Map<String, Object> value = (Map<String, Object>) change.getOrDefault("value", Collections.emptyMap());

                    // Ignora status de mensagens (entregue, lido, etc.)
                    if (value.containsKey("statuses")) continue;

                    List<Map<String, Object>> messages = (List<Map<String, Object>>) value.getOrDefault("messages", Collections.emptyList());
                    for (Map<String, Object> message : messages) {
                        String phone = (String) message.get("from");

                        // --- Resposta de botão ---
                        Map<String, Object> interactive = (Map<String, Object>) message.getOrDefault("interactive", Collections.emptyMap());
                        if (!interactive.isEmpty()) {
                            Map<String, Object> buttonReply = (Map<String, Object>) interactive.getOrDefault("button_reply", Collections.emptyMap());
                            if (!buttonReply.isEmpty()) {
                                String escolha = (String) buttonReply.get("title");
                                System.out.println("📌 " + phone + " clicou na opção: " + escolha);

                                // Envia confirmação
                                String agradecer = "Você escolheu: " + escolha + ". Agradeço muito seu feedback! 😊";
                                enviarMensagemTexto(phone, agradecer);

                                continue; // 🔑 Pula a lógica de texto
                            }
                        }

                        // --- Resposta de texto ---
                        Map<String, Object> textMap = (Map<String, Object>) message.getOrDefault("text", Collections.emptyMap());
                        String text = ((String) textMap.getOrDefault("body", "")).trim();
                        System.out.println("Mensagem recebida de " + phone + ": " + text);

                        // Se é a primeira mensagem do usuário
                        if (!perguntaEnviada.containsKey(phone)) {
                            String pergunta = "Olá! Já que iniciamos a conversa, me diga: de 0 a 10, o que você achou da oficina de Spring Boot? Sua opinião é muito importante!";
                            enviarMensagemTexto(phone, pergunta);

                            perguntaEnviada.put(phone, true); // Marca como aguardando resposta
                            continue; // 🔑 Não processa o texto recebido
                        }

                        // Se já enviou a pergunta, processa a resposta
                        if (Boolean.TRUE.equals(perguntaEnviada.get(phone))) {
                            try {
                                int nota = Integer.parseInt(text);
                                if (nota >= 0 && nota <= 10) {
                                    System.out.println("⭐️ Nota recebida de " + phone + ": " + nota);
                                    String agradecer = "Você escolheu: " + nota + "! Agradeço muito seu feedback. Qualquer coisa estou à disposição! 😊";
                                    enviarMensagemTexto(phone, agradecer);

                                    perguntaEnviada.put(phone, "respondido");
                                } else {
                                    enviarMensagemTexto(phone, "Por favor, responda com um número entre 0 e 10. 😉");
                                }
                            } catch (NumberFormatException e) {
                                enviarMensagemTexto(phone, "Não entendi sua resposta. Você poderia me dizer a nota (de 0 a 10) para a oficina de Spring Boot?");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro processando webhook: " + e.getMessage());
        }
    }
}

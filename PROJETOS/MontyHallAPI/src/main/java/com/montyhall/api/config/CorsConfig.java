package com.montyhall.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS: libera o navegador para chamar esta API a partir de outra origem.
 *
 * Um front-end em http://localhost:5173 e a API em http://localhost:8080 têm
 * portas diferentes = origens diferentes. Sem esta classe o navegador
 * bloqueia as requisições (erro de CORS no console).
 *
 * As origens permitidas vêm de CorsProperties (app.cors.allowed-origins).
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private final CorsProperties corsProperties;

    public CorsConfig(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origens = corsProperties.getAllowedOrigins().stream()
                .map(String::trim)
                .filter(origem -> !origem.isEmpty())
                .toArray(String[]::new);

        registry.addMapping("/**")
                // allowedOriginPatterns aceita endereços exatos e também curingas,
                // por exemplo https://*.vercel.app
                .allowedOriginPatterns(origens)
                // A API só usa GET, POST e DELETE (não há PUT: uma jogada não é "editada")
                .allowedMethods("GET", "POST", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}

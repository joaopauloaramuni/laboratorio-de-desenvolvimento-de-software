package com.montyhall.api.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CorsConfig e CorsProperties")
class CorsConfigTest {

    /** Expõe o método protegido getCorsConfigurations() para o teste. */
    private static class RegistroDeTeste extends CorsRegistry {
        Map<String, CorsConfiguration> configuracoes() {
            return getCorsConfigurations();
        }
    }

    private static CorsConfiguration configurar(List<String> origens) {
        CorsProperties props = new CorsProperties();
        props.setAllowedOrigins(origens);

        RegistroDeTeste registro = new RegistroDeTeste();
        new CorsConfig(props).addCorsMappings(registro);

        Map<String, CorsConfiguration> mapa = registro.configuracoes();
        assertTrue(mapa.containsKey("/**"), "deve mapear todos os caminhos");
        return mapa.get("/**");
    }

    @Test
    @DisplayName("libera as origens configuradas como padrões de origem")
    void origens() {
        CorsConfiguration cfg = configurar(List.of("http://localhost:5173", "https://montyhall.vercel.app"));

        assertEquals(List.of("http://localhost:5173", "https://montyhall.vercel.app"), cfg.getAllowedOriginPatterns());
    }

    @Test
    @DisplayName("remove espaços e ignora origens vazias")
    void limpaOrigens() {
        CorsConfiguration cfg = configurar(List.of("  http://localhost:5173  ", "", "   ", "https://*.vercel.app"));

        assertEquals(List.of("http://localhost:5173", "https://*.vercel.app"), cfg.getAllowedOriginPatterns());
    }

    @Test
    @DisplayName("aceita origem com curinga e verifica a origem de uma requisição")
    void curinga() {
        CorsConfiguration cfg = configurar(List.of("https://*.vercel.app"));

        assertEquals("https://montyhall.vercel.app", cfg.checkOrigin("https://montyhall.vercel.app"));
        assertNull(cfg.checkOrigin("https://site-malicioso.com"));
    }

    @Test
    @DisplayName("libera os métodos usados pela API e todos os cabeçalhos")
    void metodosECabecalhos() {
        CorsConfiguration cfg = configurar(List.of("http://localhost:5173"));

        assertEquals(List.of("GET", "POST", "DELETE", "OPTIONS"), cfg.getAllowedMethods());
        assertEquals(List.of("*"), cfg.getAllowedHeaders());
    }

    @Test
    @DisplayName("CorsProperties começa com lista vazia e aceita set/get")
    void corsProperties() {
        CorsProperties props = new CorsProperties();
        assertNotNull(props.getAllowedOrigins());
        assertTrue(props.getAllowedOrigins().isEmpty());

        List<String> origens = new ArrayList<>(List.of("http://localhost:5173"));
        props.setAllowedOrigins(origens);
        assertEquals(origens, props.getAllowedOrigins());
    }
}

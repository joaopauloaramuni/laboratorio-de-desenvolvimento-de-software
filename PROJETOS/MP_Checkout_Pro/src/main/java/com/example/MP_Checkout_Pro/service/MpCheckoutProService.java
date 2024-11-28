package com.example.MP_Checkout_Pro.service;

import com.example.MP_Checkout_Pro.config.ApiConfig;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

@Service
public class MpCheckoutProService {

    // private final ApiConfig apiConfig;

    public MpCheckoutProService(ApiConfig apiConfig) {
        // this.apiConfig = apiConfig;
        // Configurando o token do Mercado Pago ao iniciar o serviço
        MercadoPagoConfig.setAccessToken(apiConfig.getAccessToken());
    }

    public String criarPreferencia(String titulo, int quantidade, BigDecimal precoUnitario) throws Exception {
        PreferenceClient client = new PreferenceClient();

        PreferenceItemRequest item =
                PreferenceItemRequest.builder()
                        .title(titulo)
                        .quantity(quantidade)
                        .unitPrice(precoUnitario)
                        .build();

        PreferenceRequest request =
                PreferenceRequest.builder()
                        .items(Collections.singletonList(item))
                        .build();

        Preference preference = client.create(request);

        return preference.getInitPoint();
    }
}

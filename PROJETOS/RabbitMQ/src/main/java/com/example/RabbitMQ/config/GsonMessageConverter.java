package com.example.RabbitMQ.config;

import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.nio.charset.StandardCharsets;

public class GsonMessageConverter implements MessageConverter {

    private final Gson gson = new Gson();

    @Override
    public Message toMessage(Object object, MessageProperties props) throws MessageConversionException {
        String json = gson.toJson(object);
        props.setContentType("application/json");
        props.setHeader("__TypeId__", object.getClass().getName());
        return new Message(json.getBytes(StandardCharsets.UTF_8), props);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        String json = new String(message.getBody(), StandardCharsets.UTF_8);
        String className = message.getMessageProperties().getHeader("__TypeId__");

        try {
            Class<?> clazz = Class.forName(className);
            return gson.fromJson(json, clazz);
        } catch (ClassNotFoundException e) {
            throw new MessageConversionException("Classe não encontrada: " + className, e);
        }
    }
}
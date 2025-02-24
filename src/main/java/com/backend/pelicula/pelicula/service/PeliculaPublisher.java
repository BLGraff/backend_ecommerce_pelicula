package com.backend.pelicula.pelicula.service;

import com.backend.pelicula.config.RabbitMQConfig;
import com.backend.pelicula.pelicula.domain.Message;
import com.backend.pelicula.pelicula.domain.Pelicula;
import com.backend.pelicula.pelicula.domain.Producto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeliculaPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendPelicula(String action, Pelicula pelicula) {

        Producto producto = new Producto(pelicula.getId(), pelicula.getTitulo(), pelicula.getPrecio());

        Message mensaje = new Message(action, producto);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, mensaje);
    }
}

package com.jijy.music.presentation.controller;

import com.jijy.music.persistence.repository.ChatMessageRepository;
import com.jijy.music.presentation.dto.TextMessageDTO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller; // Usar @Controller para WebSockets

import java.time.LocalDateTime; // Para manejar la fecha y hora
import java.util.List;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class WebSocketTextController {



    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload String message) {
        System.out.println(message);
    }


    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage chatMessage,
                            SimpMessageHeaderAccessor headerAccessor) {


        // Obtener el ID de la comunidad del mensaje (por defecto "1")
        String communityId = chatMessage.getComunityId() != null ?
                chatMessage.getComunityId() : "1";

        // Determinar el tópico de destino basado en la comunidad
        String destination = "/topic/community/" + communityId;

        // Agregar información de sesión si es necesario
        String sessionId = headerAccessor.getSessionId();


        // Procesar diferentes tipos de mensajes
        switch (chatMessage.getType()) {
            case JOIN:
                chatMessage.setMessage(chatMessage.getSender() + " se ha unido al chat");
                break;

            case LEAVE:
                chatMessage.setMessage(chatMessage.getSender() + " ha abandonado el chat");
                break;

            case CHAT:
                break;
        }

        // Enviar mensaje a todos los suscriptores del tópico
        messagingTemplate.convertAndSend(destination, chatMessage);
    }

    // Endpoint adicional para enviar mensajes a una comunidad específica
    @MessageMapping("/chat.sendToCommunity")
    public void sendMessageToCommunity(@Payload ChatMessage chatMessage) {
        String communityId = chatMessage.getComunityId() != null ?
                chatMessage.getComunityId() : "1";
        String destination = "/topic/community/" + communityId;

        messagingTemplate.convertAndSend(destination, chatMessage);
    }


}
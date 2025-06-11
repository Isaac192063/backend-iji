package com.jijy.music.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebScoketController {

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload String message) {
        System.out.println(message);
    }


}

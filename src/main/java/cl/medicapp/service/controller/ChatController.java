package cl.medicapp.service.controller;

import cl.medicapp.service.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * Controlador de roles
 */
@Controller
@Slf4j
public class ChatController {

    @MessageMapping("/message")
    @SendTo("/chat/message")
    public MessageDto getMessage(MessageDto message) {

        message.setDate(new Date());

        log.info("Broker receive: " + message.toString());

        return message;
    }

}

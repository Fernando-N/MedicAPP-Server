package cl.medicapp.service.controller;

import cl.medicapp.service.dto.MessageInboundDto;
import cl.medicapp.service.dto.MessageOutboundDto;
import cl.medicapp.service.services.chat.ChatService;
import cl.medicapp.service.util.ChatUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Controlador de chat websockets
 */
@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatWSController {

    /**
     * Bean Servicio de chat
     */
    private final ChatService chatService;

    /**
     * Bean SimpMessaginTemplate para reenviar mensajes a channels
     */
    private final SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Channel encargado de recibir mensajes, guardarlos en bd y enviarlos a destino correspondiente
     * @param message Mensaje entrante
     * @param simpMessageHeaderAccessor Headers mensaje entrante
     */
    @MessageMapping("/message")
    public void sendMessage(MessageInboundDto message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {

        ChatUtil.validateHeaderContainAuthorizationValid(simpMessageHeaderAccessor);
        String emailSender = ChatUtil.getEmailSender(simpMessageHeaderAccessor);
        String idSender = ChatUtil.getIdSender(simpMessageHeaderAccessor);
        log.info("message " + message);
        log.info("EmailSender: " + emailSender);

        MessageOutboundDto messageOutboundDto = chatService.saveAndSendMessage(message, idSender);

        log.info("MessageOut: " + messageOutboundDto );

        simpMessagingTemplate.convertAndSend("/chat/messages/" + message.getTo() + "/" + idSender, messageOutboundDto);
        simpMessagingTemplate.convertAndSend("/chat/messages/" + message.getTo(), messageOutboundDto);
    }

}

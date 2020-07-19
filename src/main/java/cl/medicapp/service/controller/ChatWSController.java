package cl.medicapp.service.controller;

import cl.medicapp.service.dto.MessageInboundDto;
import cl.medicapp.service.dto.MessageOutboundDto;
import cl.medicapp.service.repository.user.UserRepository;
import cl.medicapp.service.services.chat.ChatService;
import cl.medicapp.service.util.ChatUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Controlador de roles
 */
@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatWSController {

    private final ChatService chatService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    public void sendMessage(MessageInboundDto message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {

        ChatUtil.validateHeaderContainAuthorizationValid(simpMessageHeaderAccessor);
        String emailSender = ChatUtil.getEmailSender(simpMessageHeaderAccessor);
        String idSender = ChatUtil.getIdSender(simpMessageHeaderAccessor);
        log.info("message " + message);
        log.info("EmailSender: " + emailSender);

        MessageOutboundDto messageOutboundDto = chatService.saveAndSendMessage(message, idSender);

        simpMessagingTemplate.convertAndSend("/chat/messages/" + message.getTo() + "/" + idSender, messageOutboundDto);
        simpMessagingTemplate.convertAndSend("/chat/messages/" + message.getTo(), messageOutboundDto);
    }

}

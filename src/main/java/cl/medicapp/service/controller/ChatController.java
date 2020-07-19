package cl.medicapp.service.controller;

import cl.medicapp.service.dto.MessageOutboundDto;
import cl.medicapp.service.services.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador de autenticación
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@PreAuthorize("isAuthenticated()")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/messages/get")
    public List<MessageOutboundDto> getMessages() {
        return chatService.getMessagesToUserLoggedIn();
    }

    @GetMapping("/messages/get/{userId}")
    public List<MessageOutboundDto> getMessagesFromUserId(@PathVariable String userId) {
        return chatService.getMessagesFromUserId(userId);
    }


}

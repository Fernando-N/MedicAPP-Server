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
 * Controlador de servicios de chat
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@PreAuthorize("isAuthenticated()")
public class ChatController {

    /**
     * Bean Servicio de chat
     */
    private final ChatService chatService;

    /**
     * Endpoint para obtener lista de chats iniciados
     * @return Lista de mensajes
     */
    @GetMapping("/messages/get")
    public List<MessageOutboundDto> getMessages() {
        return chatService.getMessagesToUserLoggedIn();
    }

    /**
     * Endpoint para obtener historial de chat con un usuario
     * @param userId Id de usuario con quien buscar historial
     * @return Lista de mensajes encontrados con UserId
     */
    @GetMapping("/messages/get/{userId}")
    public List<MessageOutboundDto> getMessagesFromUserId(@PathVariable String userId) {
        return chatService.getMessagesFromUserId(userId);
    }

}

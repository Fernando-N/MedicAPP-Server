package cl.medicapp.service.controller;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.MessageOutboundDto;
import cl.medicapp.service.dto.ResetPasswordRequestDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.services.auth.AuthService;
import cl.medicapp.service.services.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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

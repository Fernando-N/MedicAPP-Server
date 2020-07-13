package cl.medicapp.service.controller;

import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.MessageDto;
import cl.medicapp.service.repository.user.UserRepository;
import cl.medicapp.service.services.chat.ChatService;
import cl.medicapp.service.util.ChatUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

/**
 * Controlador de roles
 */
@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;

    @MessageMapping("/message")
    @SendTo("/chat/message")
    public MessageDto getMessage(MessageDto message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {

        ChatUtil.validateHeaderContainAuthorizationValid(simpMessageHeaderAccessor);


        String emailSender = ChatUtil.getEmailSender(simpMessageHeaderAccessor);

        Optional<UserDocument> userDocument = userRepository.findByEmailIgnoreCase(emailSender);


        message.setFrom(MessageDto.UserChat.builder()
                .id(userDocument.get().getId())
                .name(userDocument.get().getUserDetails().getFirstName() + " " + userDocument.get().getUserDetails().getLastName())
                .avatar(userDocument.get().getUserDetails().getProfileImageURI())
                .build());




        log.info("Broker receive: " + message.toString());


        return message;
    }

}

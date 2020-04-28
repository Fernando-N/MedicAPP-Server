package cl.medicapp.service.controller;

import cl.medicapp.service.dto.MessageDto;
import cl.medicapp.service.services.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

/**
 * Controlador de roles
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void postChat(@Valid @RequestBody MessageDto messageDto) {
        chatService.insertAndSubscribe(messageDto);
    }

    @GetMapping(value = "/{to}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessageDto> streamMessages(@PathVariable String to) {
        return chatService.openStreamToUser(to);
    }

}

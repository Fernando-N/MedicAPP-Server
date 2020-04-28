package cl.medicapp.service.controller;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Controlador de autenticación
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
//@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @GetMapping("")
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{email}")
    public UserDto getByEmail(@RequestParam String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/{firstName}-{lastName}")
    public List<UserDto> getByName(@RequestParam String firstName, @RequestParam String lastName) {
        return userService.getByName(firstName, lastName);
    }

    @DeleteMapping("/{email}")
    public GenericResponseDto deleteByEmail(String email) {
        return userService.deleteByEmail(email);
    }

}

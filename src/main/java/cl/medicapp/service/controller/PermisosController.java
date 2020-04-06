package cl.medicapp.service.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermisosController {

    @GetMapping(value = "/ex")
    public void exception() throws Exception {
        throw new Exception();
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/any")
    public String testMethod() {
        return "any!";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin")
    public String testMethod2() {
        return "admin";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/user")
    public String testMethod3() {
        return "user";
    }

}

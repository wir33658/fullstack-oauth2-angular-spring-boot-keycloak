package com.example.demo.controller;

import com.example.demo.jwt.CustomJwt;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@CrossOrigin(
        origins = "http://localhost:4200",
//        origins = "https://fuzzy-guacamole-x9vj4qwx763v959-4200.app.github.dev",
        allowedHeaders = "*",
        methods = { RequestMethod.GET }
)
public class HelloController {
    private int counter = 1;

    @GetMapping("/test")
    public Message test() {
        var message = "Simple Test2";
        return new Message(message);
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('ROLE_fullstack-developer')")
    public Message hello() {
        var jwt = (CustomJwt) SecurityContextHolder.getContext().getAuthentication();
        var message = MessageFormat
                .format("Hello[{2}] fullstack master {0} {1}, how is it going today?",
                        jwt.getFirstname(), jwt.getLastname(), counter);
        counter += 1;
        return new Message(message);
    }

    record Message(String message) {}
}

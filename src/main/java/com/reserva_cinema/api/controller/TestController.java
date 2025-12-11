package com.reserva_cinema.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/public/hello")
    public String publicHello() {
        return "Hello, World! This is a public endpoint.";
    }

    @GetMapping("/private/hello")
    public String privateHello() {
        return "Hello, World! This is a private endpoint.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/private/admin")
    public String adminHello() {
        return "Hello, Admin! This is an admin-only endpoint.";
    }
}

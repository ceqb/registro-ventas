package com.VentaCliente.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/")
    public String hello() {
        return "¡Servidor activo desde Render!";
    }
}

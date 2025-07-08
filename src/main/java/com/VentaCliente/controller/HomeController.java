package com.VentaCliente.controller;

import com.VentaCliente.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequiredArgsConstructor
public class HomeController {
    private final VentaService ventaService;
    @GetMapping({"/home" })
    public String biblioteca(Model modelo) {
        modelo.addAttribute("listVenta", ventaService.listVenta());
        return "home"; // nos retorna al archivo estudiantes
    }
}

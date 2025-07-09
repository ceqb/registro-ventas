package com.VentaCliente.login;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 🧼 Limpia la sesión
        return "redirect:/login";
    }
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String usuario,
                                @RequestParam String clave,
                                Model model,
                                HttpSession session) {

        Optional<UsuarioDTO> user = usuarioService.login(usuario, clave);
        if (user.isPresent()) {
            session.setAttribute("usuarioLogueado", user.get()); // 🔐 Guardar en sesión
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
            return "login";
        }
    }
}
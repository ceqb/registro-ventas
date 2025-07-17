package com.VentaCliente.login;

import com.VentaCliente.service.AuditoriaAccesoService;
import com.VentaCliente.service.AuditoriaClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuditoriaAccesoService auditoriaAccesoService;

    @GetMapping("/preloader")
    public String mostrarPreloader() {
        return "preloader"; // Spring buscará templates/preloader.html
    }

    //-------------------------LOGIN----------------------------//
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
                                HttpSession session,
                                HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String userAgent = request.getHeader("User-Agent");
        try {
        Optional<UsuarioDTO> user = usuarioService.login(usuario, clave);
        if (user.isPresent()) {
            session.setAttribute("usuarioLogueado", user.get());

            auditoriaAccesoService.registrarAuditoriaAcceso(
                    usuario,"La ip con la que accedio: " + ip, "logro acceder al Login de forma exitosa " + userAgent, true, "Login exitoso"
            );

            return "redirect:/preloader";
        } else {
            auditoriaAccesoService.registrarAuditoriaAcceso(
                    usuario,
                    "La ip con la que se intento acceder: " + ip,
                    userAgent,
                    false,
                    "Credenciales incorrectas"
            );

            model.addAttribute("error", "Usuario o contraseña incorrectos.");
            return "login";
        }
        } catch (RuntimeException ex) {
            auditoriaAccesoService.registrarAuditoriaAcceso(
                    usuario, "IP: " + ip, userAgent, false, "Intento de acceso bloqueado"
            );
            model.addAttribute("error", ex.getMessage());
            return "desbloquearUsuario"; // redirige al formulario
        }
    }
    //-------------------------DESBLOQUEO----------------------------//
    @GetMapping("/desbloquearUsuario")
    public String mostrarFormularioDesbloqueo() {
        return "desbloquearUsuario"; // Spring buscará templates/preloader.html
    }

    @PostMapping("/desbloquearUsuario")
    public String procesarDesbloqueo(@RequestParam String usuario,
                                     @RequestParam String codigo,
                                     Model model,
                                     HttpServletRequest request) {
        System.out.println("👀 Usuario recibido desde el formulario: " + usuario);
        System.out.println("🔑 Código recibido: " + codigo);
        try{
            usuarioService.desbloquearUsuario(usuario, codigo,request);

            model.addAttribute("success", "Cuenta desbloqueada correctamente.");
            return "redirect:/login";
        }catch(RuntimeException e){
            model.addAttribute("error", e.getMessage());
            return "desbloquearUsuario";
        }
    }
    //-------------------------REENVIO DE CODIGO EXPIRADO 10 MINUTOS----------------------------//
    @PostMapping("/reenviar-codigo")
    public String reenviarCodigoDesbloqueo(@RequestParam String usuario,
                                           Model model) {
        try {
            usuarioService.reenviarCodigoDesbloqueo(usuario); // lógica en la implementación
            model.addAttribute("success", "✅ Código reenviado correctamente al correo.");
            model.addAttribute("usuario", usuario); // mantener el valor en el input
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("usuario", usuario); // mantener el valor en el input
        }

        return "desbloquearUsuario";
    }
}
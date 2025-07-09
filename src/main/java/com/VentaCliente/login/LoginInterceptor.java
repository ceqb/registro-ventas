package com.VentaCliente.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        boolean estaLogueado = session != null && session.getAttribute("usuarioLogueado") != null;
        String uri = request.getRequestURI();

        // Permitir acceso al login, css, imágenes y otros recursos públicos
        if (estaLogueado || uri.contains("/login") || uri.contains("/css") || uri.contains("/js") || uri.contains("/img")) {
            return true;
        }

        // Redirigir al login si no está autenticado
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
}

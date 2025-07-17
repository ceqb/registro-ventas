package com.VentaCliente.login;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface UsuarioService {

    Optional<UsuarioDTO> login(String usuario, String clave);
    Optional<Usuario> buscarPorUsuario(String usuario);
    UsuarioDTO actualizar(UsuarioDTO dto); //Mejor si usas DTOs entre capas.
    UsuarioDTO desbloquearUsuario(String usuario, String codigo, HttpServletRequest request);
    UsuarioDTO reenviarCodigoDesbloqueo(String usuario);
}

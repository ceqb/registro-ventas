package com.VentaCliente.login;

import java.util.Optional;

public interface UsuarioService {

    Optional<UsuarioDTO> login(String usuario, String clave);
}

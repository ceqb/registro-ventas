package com.VentaCliente.login;


import com.VentaCliente.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UsuarioDTO> login(String usuario, String clave) {
        return usuarioRepository.findByUsuario(usuario)
                .filter(usuarioEntidad -> passwordEncoder.matches(clave, usuarioEntidad.getClave()))
                .map(UsuarioDTO::toDto);
    }

}

package com.VentaCliente.login;


import com.VentaCliente.exception.ResourceNotFoundException;
import com.VentaCliente.model.CorreoProperties;
import com.VentaCliente.service.AuditoriaAccesoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditoriaAccesoService auditoriaAccesoService;
    private final CorreoProperties correoProperties;
    private String generarCodigoDesbloqueo() {
        return String.valueOf((int) (Math.random() * 900000 + 100000)); // Código de 6 dígitos
    }

    @Autowired
    private JavaMailSender mailSender;

    private void enviarCorreoDesbloqueo(String correo, String codigo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correo);
        mensaje.setSubject("🔐 Código de desbloqueo");
        mensaje.setText("Tu cuenta ha sido bloqueada por 3 intentos fallidos.\n\n" +
                "Código para desbloquear: " + codigo + "\n" +
                "Este código expirará en 10 minutos.");
        mensaje.setFrom("crearveint@gmail.com"); // Usa tu remitente configurado
        mailSender.send(mensaje);
    }

    @Override
    public Optional<UsuarioDTO> login(String usuario, String clave) {
        Optional<Usuario> userOpt = usuarioRepository.findByUsuario(usuario);

        if (userOpt.isEmpty()) return Optional.empty();

        Usuario user = userOpt.get();

        if (user.isBloqueado()) {
            throw new RuntimeException("Tu cuenta esta bloqueada. Debes desbloquearla para continuar");
        }

        if (passwordEncoder.matches(clave, user.getClave())) {
            user.setIntentosFallidos(0);
            usuarioRepository.save(user);
            return Optional.of(UsuarioDTO.toDto(user));
        } else {
            user.setIntentosFallidos(user.getIntentosFallidos() + 1);

            if (user.getIntentosFallidos() >= 3) {
                user.setBloqueado(true);
                String codigo = generarCodigoDesbloqueo();
                user.setCodigoDesbloqueo(codigo);
                user.setExpiracionCodigo(LocalDateTime.now().plusMinutes(10));

                // Envío de correo
                enviarCorreoDesbloqueo(user.getCorreo(), codigo);
            }

            usuarioRepository.save(user);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Usuario> buscarPorUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    @Override
    public UsuarioDTO actualizar(UsuarioDTO usuarioDTO) {
        log.info("🛠️ Ejecutando método actualizar para el usuario: {}", usuarioDTO.getUsuario());

        if (usuarioDTO.getId() == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo para una actualización.");
        }
        log.info("🛠️ Ejecutando método actualizar para el usuario: {}", usuarioDTO.getUsuario());

        Usuario existente = usuarioRepository.findById(usuarioDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioDTO.getId()));
        // Actualizamos solo los campos que quieras permitir cambiar
        existente.setIntentosFallidos(usuarioDTO.getIntentosFallidos());
        existente.setBloqueado(usuarioDTO.isBloqueado());
        existente.setCodigoDesbloqueo(usuarioDTO.getCodigoDesbloqueo());
        existente.setExpiracionCodigo(usuarioDTO.getExpiracionCodigo());

        // puedes agregar más campos si lo deseas
        Usuario guardado = usuarioRepository.save(existente);


        return UsuarioDTO.toDto(guardado);
    }

    @Override
    public UsuarioDTO desbloquearUsuario(String usuario, String codigo, HttpServletRequest request) {
        Usuario user = usuarioRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        if (!user.isBloqueado()) {
            throw new RuntimeException("Este usuario no está bloqueado.");
        }

        if (user.getCodigoDesbloqueo() == null || !user.getCodigoDesbloqueo().equals(codigo)) {
            throw new RuntimeException("Código incorrecto.");
        }

        if (user.getExpiracionCodigo() == null || user.getExpiracionCodigo().isBefore(LocalDateTime.now())) {
            // 🔁 Código expirado: generar uno nuevo
            String nuevoCodigo = generarCodigoDesbloqueo();
            user.setCodigoDesbloqueo(nuevoCodigo);
            user.setExpiracionCodigo(LocalDateTime.now().plusMinutes(10));
            usuarioRepository.save(user);

            // 📧 Reenviar correo
            enviarCorreoDesbloqueo(user.getCorreo(), nuevoCodigo);

            throw new RuntimeException("El código ha expirado. Se ha enviado uno nuevo al correo.");
        }

        // 🔓 Desbloqueo y limpieza
        user.setBloqueado(false);
        user.setIntentosFallidos(0);
        user.setCodigoDesbloqueo(null);
        user.setExpiracionCodigo(null);

        Usuario actualizado = usuarioRepository.save(user);

        // Auditoría del desbloqueo
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        String userAgent = request.getHeader("User-Agent");

        auditoriaAccesoService.registrarAuditoriaAcceso(
                usuario,
                "Desbloqueo desde IP: " + ip,
                userAgent,
                true,
                "Desbloqueo exitoso con código"
        );
        return UsuarioDTO.toDto(actualizado);
    }

    @Override
    public UsuarioDTO reenviarCodigoDesbloqueo(String usuario) {
        Optional<Usuario> userOpt = usuarioRepository.findByUsuario(usuario);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado.");
        }

        Usuario user = userOpt.get();

        if (!user.isBloqueado()) {
            throw new RuntimeException("Este usuario no está bloqueado.");
        }

        String nuevoCodigo = generarCodigoDesbloqueo();
        user.setCodigoDesbloqueo(nuevoCodigo);
        user.setExpiracionCodigo(LocalDateTime.now().plusMinutes(10));
        Usuario codigoReenviado = usuarioRepository.save(user);

        enviarCorreoDesbloqueo(user.getCorreo(), nuevoCodigo);

        return UsuarioDTO.toDto(codigoReenviado); // ✅ Solo este return
    }
    }




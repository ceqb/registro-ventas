package com.VentaCliente.service;

import com.VentaCliente.model.*;
import com.VentaCliente.repository.HistorialEnvioDeudaRepository;
import com.VentaCliente.repository.HistorialEnvioPersonalizadoRepository;
import com.VentaCliente.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificacionPersonalizadaDeudaService {
/*

    private final VentaRepository ventaRepository;
    private final JavaMailSender mailSender;
    private final HistorialEnvioPersonalizadoRepository historialRepository;
    private final CorreoProperties correoProperties;

    @Scheduled(cron = "0 0 10 * * *") // Cada día a las 10 AM
    public void enviarNotificacionesIndividuales() {
        List<Venta> deudas = ventaRepository.findByEstado(EstadoVenta.DEUDA);

        if (deudas.isEmpty()) {
            System.out.println("✅ No hay clientes con deuda para notificar.");
            return;
        }

        int enviados = 0;

        for (Venta venta : deudas) {
            // Verifica si ya hubo intento exitoso
            Optional<HistorialEnvioPersonalizado> ultimoEnvioOpt = historialRepository.findTopByVentaOrderByFechaEnvioDesc(venta);

            if (ultimoEnvioOpt.isPresent() && ultimoEnvioOpt.get().isEnvioExitoso()) {
                // Si ya fue enviado con éxito, saltamos
                continue;
            }

            String correoCliente = venta.getCliente().getCorreo();
            String nombreCliente = venta.getCliente().getNombre();
            boolean envioExitoso = true;
            String observacion;

            if (correoCliente == null || correoCliente.isBlank()) {
                envioExitoso = false;
                observacion = "⚠️ Cliente sin correo.";
                historialRepository.save(HistorialEnvioPersonalizado.builder()
                        .venta(venta)
                        .fechaEnvio(LocalDateTime.now())
                        .envioExitoso(false)
                        .observaciones(observacion)
                        .build()
                );
                System.out.println("❌ Cliente sin correo: " + nombreCliente);
                continue;
            }

            String mensaje = String.format("""
                    Estimado/a %s,

                    Tiene una deuda pendiente por:

                    Producto: %s
                    Cantidad: %d
                    Total: S/ %.2f

                    Por favor regularice su deuda.

                    Gracias.
                    """, nombreCliente, venta.getProducto(), venta.getCantidad(), venta.getTotal());

            try {
                SimpleMailMessage correo = new SimpleMailMessage();
                correo.setTo(correoCliente);
                correo.setSubject("🔔 Recordatorio de Deuda");
                correo.setText(mensaje);
                correo.setFrom(correoProperties.getFrom());

                mailSender.send(correo);
                observacion = "Correo enviado correctamente.";
                enviados++;

            } catch (Exception e) {
                envioExitoso = false;
                observacion = "❌ Error al enviar correo: " + e.getMessage();
                System.err.println(observacion);
            }

            historialRepository.save(HistorialEnvioPersonalizado.builder()
                    .venta(venta)
                    .fechaEnvio(LocalDateTime.now())
                    .envioExitoso(envioExitoso)
                    .observaciones(observacion)
                    .build()
            );
        }

        System.out.println("📤 Correos reenviados (solo fallidos): " + enviados);
    }*/
}



package com.VentaCliente.service;

import com.VentaCliente.model.EstadoVenta;
import com.VentaCliente.model.HistorialEnvioDeuda;
import com.VentaCliente.model.HistorialEnvioPersonalizado;
import com.VentaCliente.model.Venta;
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

@Service
@RequiredArgsConstructor
public class RecordatorioDeudaServiceGLOBALeINDIVIDUAL {
    /*private final VentaRepository ventaRepository;
    private final JavaMailSender mailSender;
    private final HistorialEnvioDeudaRepository historialEnvioRepository;
    private final HistorialEnvioPersonalizadoRepository historialEnvioPersonalizadoRepository;

    @Scheduled(cron = "0 30 16 * * *") // TEMPORAL: A las 4:30 PM
    public void enviarNotificacionesPorCliente() {
        List<Venta> deudas = ventaRepository.findByEstado(EstadoVenta.DEUDA);
        LocalDateTime ahora = LocalDateTime.now();

        if (deudas.isEmpty()) {
            historialEnvioRepository.save(HistorialEnvioDeuda.builder()
                    .fechaEnvio(ahora)
                    .totalDeudas(0)
                    .envioExitoso(true)
                    .observaciones("No había deudas para notificar.")
                    .build()
            );
            System.out.println("✅ No hay deudas hoy.");
            return;
        }

        int enviados = 0;

        for (Venta venta : deudas) {
            String correoCliente = venta.getCliente().getCorreo();

            if (correoCliente == null || correoCliente.isBlank()) {
                continue; // saltar si no tiene correo
            }

            try {
                String mensajePersonal = String.format(
                        "Estimado/a %s,\n\n" +
                                "Le recordamos que tiene una deuda pendiente por el producto: %s.\n" +
                                "Monto total: S/ %.2f\n\n" +
                                "Por favor, regularice su deuda lo antes posible.\n\nAtentamente,\nLa empresa.",
                        venta.getCliente().getNombre(),
                        venta.getProducto(),
                        venta.getTotal()
                );

                SimpleMailMessage mensaje = new SimpleMailMessage();
                mensaje.setTo(correoCliente);
                mensaje.setSubject("🔔 Recordatorio de deuda pendiente");
                mensaje.setText(mensajePersonal);
                mensaje.setFrom("tu_correo@gmail.com"); // reemplazar por CorreoProperties si deseas

                mailSender.send(mensaje);
                enviados++;

                historialEnvioPersonalizadoRepository.save(
                        HistorialEnvioPersonalizado.builder()
                                .venta(venta)
                                .fechaEnvio(ahora)
                                .envioExitoso(true)
                                .observaciones("Correo enviado correctamente.")
                                .build()
                );

            } catch (Exception e) {
                historialEnvioPersonalizadoRepository.save(
                        HistorialEnvioPersonalizado.builder()
                                .venta(venta)
                                .fechaEnvio(ahora)
                                .envioExitoso(false)
                                .observaciones("Error al enviar correo: " + e.getMessage())
                                .build()
                );

                System.err.println("❌ Error al enviar a " + correoCliente + ": " + e.getMessage());
            }
        }

        historialEnvioRepository.save(HistorialEnvioDeuda.builder()
                .fechaEnvio(ahora)
                .totalDeudas(deudas.size())
                .envioExitoso(true)
                .observaciones("Correos personalizados enviados a " + enviados + " clientes.")
                .build()
        );

        System.out.println("✅ Correos personalizados enviados: " + enviados);
    }*/
}

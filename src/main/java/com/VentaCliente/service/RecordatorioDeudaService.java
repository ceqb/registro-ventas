package com.VentaCliente.service;


import com.VentaCliente.model.CorreoProperties;
import com.VentaCliente.model.EstadoVenta;
import com.VentaCliente.model.HistorialEnvioDeuda;
import com.VentaCliente.model.Venta;
import com.VentaCliente.repository.HistorialEnvioDeudaRepository;
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

public class RecordatorioDeudaService {
    private final VentaRepository ventaRepository;
    private final JavaMailSender mailSender;
    private final HistorialEnvioDeudaRepository historialEnvioRepository;
    private final CorreoProperties correoProperties;


   // @Scheduled(cron = "0 0 9 * * *") // Todos los días a las 9:00 AM
    @Scheduled(cron = "0 0 21 * * *") // Todos los días a las 9:00 PM
    public void enviarResumenDeudasAlAdmin() {
        List<Venta> deudas = ventaRepository.findByEstado(EstadoVenta.DEUDA);
        LocalDateTime ahora = LocalDateTime.now();

        if (deudas.isEmpty()) {
            historialEnvioRepository.save(HistorialEnvioDeuda.builder()
                    .fechaEnvio(ahora)
                    .totalDeudas(0)
                    .envioExitoso(true)
                    .observaciones("No había deudas para enviar.")
                    .build()
            );
            System.out.println("✅ No hay ventas con deuda hoy.");
            return;
        }

        StringBuilder resumen = new StringBuilder("📋 RESUMEN DE DEUDAS:\n\n");
        for (Venta venta : deudas) {
            resumen.append(String.format("• Cliente: %s | Producto: %s | Total: S/ %.2f\n",
                    venta.getCliente().getNombre(), venta.getProducto(), venta.getTotal()));
        }

        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(correoProperties.getAdmin());
            mensaje.setSubject("🔔 Reporte de clientes con deuda");
            mensaje.setText(resumen.toString());
            mensaje.setFrom(correoProperties.getFrom());
            System.out.println("📧 Enviando a: " + correoProperties.getAdmin());
            System.out.println("📤 Desde: " + correoProperties.getAdmin());
            mailSender.send(mensaje);

            historialEnvioRepository.save(HistorialEnvioDeuda.builder()
                    .fechaEnvio(ahora)
                    .totalDeudas(deudas.size())
                    .envioExitoso(true)
                    .observaciones("Correo enviado correctamente.")
                    .build()
            );

            System.out.println("✅ Correo enviado y registrado.");

        } catch (Exception e) {
            historialEnvioRepository.save(HistorialEnvioDeuda.builder()
                    .fechaEnvio(ahora)
                    .totalDeudas(deudas.size())
                    .envioExitoso(false)
                    .observaciones("Error al enviar correo: " + e.getMessage())
                    .build()
            );

            System.err.println("❌ Error al enviar el correo: " + e.getMessage());
        }
    }

}

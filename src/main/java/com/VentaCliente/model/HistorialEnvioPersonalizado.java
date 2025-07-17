package com.VentaCliente.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_envio_personalizado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialEnvioPersonalizado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "destinatario")
    private String destinatario;

    @Column(name = "asunto")
    private String asunto;

    @Column(length = 500)
    private String mensaje;

    @Column(name = "envioExitoso")
    private boolean envioExitoso;

    @Column(name = "fechaEnvio")
    private LocalDateTime fechaEnvio;

    @Column(name = "observaciones")
    private String observaciones;

    // Relación ManyToOne con Venta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;
}

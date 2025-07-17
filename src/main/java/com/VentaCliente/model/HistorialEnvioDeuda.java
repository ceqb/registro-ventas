package com.VentaCliente.model;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "historial_envio_deuda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialEnvioDeuda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fechaEnvio")
    private LocalDateTime fechaEnvio;

    @Column(name = "totalDeudas")
    private int totalDeudas;

    @Column(name = "envioExitoso")
    private boolean envioExitoso;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

}

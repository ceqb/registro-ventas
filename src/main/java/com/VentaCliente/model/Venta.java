package com.VentaCliente.model;

import com.VentaCliente.dto.ClienteDTO;
import com.VentaCliente.dto.VentaDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ventas")
public class Venta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_ventas;

    @Column(name = "producto")
    private String producto;

    @Column(name = "fecha_venta")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaVenta;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precioUnitario")
    private Double precioUnitario;

    @Column(name = "total")
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoVenta estado;

    @ManyToOne//no lleva el arraylist, solo lleva cuando en OneToMany
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public static Venta toModel(VentaDTO dto) {
        if (dto == null) return null;

        Cliente cliente = null;
        if (dto.getClienteDTO() != null && dto.getClienteDTO().getId() != null) {
            cliente = new Cliente();
            cliente.setId(dto.getClienteDTO().getId()); // solo el ID, JPA lo resuelve
        }

        return Venta.builder()
                .id_ventas(dto.getId_ventas())
                .producto(dto.getProducto())
                .fechaVenta(dto.getFechaVenta())
                .cantidad(dto.getCantidad())
                .precioUnitario(dto.getPrecioUnitario())
                .total(dto.getCantidad() * dto.getPrecioUnitario())
                .estado(dto.getEstado() != null ? EstadoVenta.valueOf(dto.getEstado()) : null)
                .cliente(cliente)
                .build();
    }
    public static List<Venta> toModel(List<VentaDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream()
                .map(Venta::toModel).collect(Collectors.toList());
    }
}

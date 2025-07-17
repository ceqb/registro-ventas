package com.VentaCliente.dto;

import com.VentaCliente.model.Cliente;
import com.VentaCliente.model.EstadoVenta;
import com.VentaCliente.model.Venta;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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


@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VentaDTO implements Serializable {

    private Long id_ventas;
    private String producto;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaVenta;
    private Integer cantidad;
    private Double precioUnitario;
    private Double total;
    // Enum como texto (ideal para lógica backend/frontend)
    private String estado;

    // Descripción legible (ideal para mostrar en UI)
    private String estadoDescripcion;
    private ClienteDTO clienteDTO;

    public static VentaDTO toDto(Venta model) {
        if (model == null) return null;

        return VentaDTO.builder()
                .id_ventas(model.getId_ventas())
                .producto(model.getProducto())
                .fechaVenta(model.getFechaVenta())
                .cantidad(model.getCantidad())
                .precioUnitario(model.getPrecioUnitario())
                .total(model.getTotal())
                .estado(model.getEstado() != null ? model.getEstado().name() : null) // Mapeo de Enum a String
                .estadoDescripcion(model.getEstado() != null ? model.getEstado().getDescripcion() : null)

                // Evita volver a convertir todas las ventas del cliente para cortar el ciclo
                .clienteDTO(ClienteDTO.builder()
                        .id(model.getCliente().getId())
                        .nombre(model.getCliente().getNombre())
                        .telefono(model.getCliente().getTelefono())
                        .build())
                .build();
    }
    public static List<VentaDTO> toDto(List<Venta> models) {
        if (models == null) return Collections.emptyList();
        return models.stream()
                .map(VentaDTO::toDto).collect(Collectors.toList());
    }
}
